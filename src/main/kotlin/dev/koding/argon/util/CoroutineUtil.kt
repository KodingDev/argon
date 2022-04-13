@file:OptIn(DelicateCoroutinesApi::class)

package dev.koding.argon.util

import kotlinx.coroutines.*

suspend fun <T, R> List<T>.mapThreaded(threads: Int, block: suspend (T) -> R): List<R> {
    val context = newFixedThreadPoolContext(threads, "Threaded")
    return map {
        CoroutineScope(context).async {
            runBlocking { block(it) }
        }
    }.awaitAll()
}