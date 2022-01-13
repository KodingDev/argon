package dev.koding.argon.metrics.prometheus

import dev.koding.argon.data.config
import dev.koding.argon.metrics.MetricManager
import io.prometheus.client.Collector
import io.prometheus.client.exporter.BasicAuthHttpConnectionFactory
import io.prometheus.client.exporter.PushGateway
import kotlinx.coroutines.*
import mu.KotlinLogging
import java.net.URL
import kotlin.math.max
import kotlin.system.measureTimeMillis

class PrometheusExporter(private val manager: MetricManager) {

    private val logger = KotlinLogging.logger { }
    private val coroutineScope = CoroutineScope(Dispatchers.IO) + SupervisorJob()

    lateinit var gateway: PushGateway

    fun connect() {
        val url = config.prometheus?.url ?: return logger.warn { "Not initializing Prometheus as it is not configured" }
        gateway = PushGateway(URL(url))

        if (config.prometheus.username != null) {
            gateway.setConnectionFactory(
                BasicAuthHttpConnectionFactory(
                    config.prometheus.username,
                    config.prometheus.password
                )
            )
        }

        scheduleTasks()
    }

    private fun scheduleTasks() {
        val interval = config.prometheus?.interval
            ?: return logger.warn { "Not scheduling Prometheus tasks as it is not configured" }

        coroutineScope.launch {
            while (true) {
                val time = measureTimeMillis {
                    try {
                        val collection = SampleCollection(manager.collect().toPrometheus())
                        gateway.push(collection, config.prometheus.job)
                        logger.debug { "Successfully pushed metrics to Prometheus" }
                    } catch (e: Throwable) {
                        logger.error(e) { "Error while collecting metrics" }
                    }
                }
                delay(max(0, interval * 1000 - time))
            }
        }
    }

    class SampleCollection(private val collection: List<MetricFamilySamples>) : Collector() {
        override fun collect() = collection
    }

}