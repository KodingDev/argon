@file:OptIn(ExperimentalSerializationApi::class)

package dev.koding.argon.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File

val json = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
}

inline fun <reified T> Json.decodeFromFile(file: File) = decodeFromStream<T>(file.inputStream())