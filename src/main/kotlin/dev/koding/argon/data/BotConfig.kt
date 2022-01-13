package dev.koding.argon.data

import dev.koding.argon.util.decodeFromFile
import dev.koding.argon.util.json
import kotlinx.serialization.Serializable
import java.io.File

val config = json.decodeFromFile<BotConfig>(File("config.json"))

@Serializable
data class BotConfig(
    val discord: Discord = Discord(),
    val prometheus: Prometheus? = null,
    val metrics: Metrics = Metrics(),
    val clockify: Clockify? = null
) {
    @Serializable
    data class Discord(
        val token: String = "",
        val guildId: String? = null,
        val ownerId: String? = null,
    )

    @Serializable
    data class Prometheus(
        val url: String = "http://localhost:9091",
        val username: String? = null,
        val password: String? = null,
        val interval: Int = 30,
        val job: String = "argon",
    )

    @Serializable
    data class Metrics(
        val publicDiscordBots: PublicDiscordBotMetric? = null
    ) {
        @Serializable
        data class PublicDiscordBotMetric(
            val token: String = "", // This MUST be a user token, not a bot token
            val clientIds: List<String> = emptyList(),
        )
    }

    @Serializable
    data class Clockify(
        val apiKey: String = "",
    )
}