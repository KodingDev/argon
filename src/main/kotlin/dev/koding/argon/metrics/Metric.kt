package dev.koding.argon.metrics

typealias Labels = Map<String, String>

sealed class Metric(
    open val name: String,
    open val labels: Labels = emptyMap()
)

data class Gauge(
    override val name: String,
    val value: Number,
    override val labels: Labels = emptyMap()
) : Metric(name, labels)

interface Collector {
    fun collect(): List<Metric>
}

interface CollectorCollection {
    fun enabled() = true
    val collectors: List<Collector>
}

fun CollectorCollection.collect() = collectors.flatMap { it.collect() }