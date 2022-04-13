package dev.koding.argon.util

import com.kotlindiscord.kord.extensions.DiscordRelayedException
import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.string
import dev.koding.argon.util.web3.isAddress
import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import dev.kord.rest.builder.message.create.MessageCreateBuilder
import dev.kord.rest.builder.message.create.embed

@Suppress("unused")
object Colors {
    val SUCCESS = Color(0x4caf50)
    val ERROR = Color(0xf44336)
    val INFO = Color(0x2196f3)
}

suspend fun MessageCreateBuilder.feedback(message: String? = null, builder: suspend EmbedBuilder.() -> Unit = {}) {
    embed {
        color = Colors.SUCCESS
        description = message
        builder()
    }
}

fun Arguments.address(
    displayName: String,
    description: String
) = string(displayName, description) { arg, value ->
    if (!value.isAddress()) {
        throw DiscordRelayedException("Invalid address: ${arg.displayName}")
    }
}

fun discordError(message: String): Nothing = throw DiscordRelayedException(message)