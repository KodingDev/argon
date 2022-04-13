package dev.koding.argon.util

import java.text.NumberFormat
import java.util.*

@Suppress("unused")
fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}

fun Double.asCurrency(): String = NumberFormat.getCurrencyInstance(Locale.US).format(this)

fun Double.toFixed(decimals: Int): String = String.format("%.${decimals}f", this)