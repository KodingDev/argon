package dev.koding.argon.metrics.prometheus

import dev.koding.argon.metrics.Gauge
import dev.koding.argon.metrics.Metric
import io.prometheus.client.Collector

fun List<Metric>.toPrometheus(): List<Collector.MetricFamilySamples> {
    val map = mutableMapOf<String, MutableList<Metric>>()
    forEach { map.computeIfAbsent(it.name) { mutableListOf() }.add(it) }

    return map.map { (name, metrics) ->
        val type: Collector.Type
        val samples: MutableList<Collector.MetricFamilySamples.Sample>

        when (metrics.first()) {
            is Gauge -> {
                type = Collector.Type.GAUGE
                samples = mutableListOf()
            }
        }

        metrics.forEach {
            val (labelKeys, labelValues) = it.labels.map { (k, v) -> k to v }.unzip()

            @Suppress("USELESS_IS_CHECK")
            when (it) {
                is Gauge -> {
                    samples.add(
                        Collector.MetricFamilySamples.Sample(
                            name,
                            labelKeys,
                            labelValues,
                            it.value.toDouble()
                        )
                    )
                }
            }
        }

        Collector.MetricFamilySamples(name, type, "", samples)
    }
}