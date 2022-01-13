package dev.koding.argon.util

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

val httpClient = HttpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer(json)
    }
}