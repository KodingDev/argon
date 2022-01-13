package dev.koding.argon.metrics

import dev.koding.argon.metrics.prometheus.PrometheusExporter
import mu.KotlinLogging

class MetricManager {

    private val logger = KotlinLogging.logger { }
    private val collectors = mutableListOf<CollectorCollection>()

    fun start() {
        if (collectors.isEmpty()) {
            logger.info { "No collectors found, skipping metric collection" }
            return
        }

        PrometheusExporter(this).connect()
    }

    private fun register(collector: CollectorCollection) = apply {
        if (!collector.enabled()) return@apply
        collectors.add(collector)
    }

    @Suppress("unused")
    fun register(vararg collectors: Collector) =
        register(object : CollectorCollection {
            override val collectors: List<Collector> = collectors.toList()
        })

    fun collect() = collectors.flatMap { it.collect() }

}