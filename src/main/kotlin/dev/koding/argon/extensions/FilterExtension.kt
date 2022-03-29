package dev.koding.argon.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.application.slash.EphemeralSlashCommand
import com.kotlindiscord.kord.extensions.commands.application.slash.ephemeralSubCommand
import com.kotlindiscord.kord.extensions.commands.converters.impl.optionalBoolean
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.ephemeralSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import dev.koding.argon.data.config
import dev.koding.argon.util.ownerOnly
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.event.message.MessageCreateEvent
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.reflect.KMutableProperty

class FilterExtension(override val name: String = "Filter") : Extension() {
    var swearFilterEnabled = false

    override suspend fun setup() {
        ephemeralSlashCommand {
            name = "filter"
            description = "Toggle filter"
            ownerOnly()

            addFilterToggle("swearing", this@FilterExtension::swearFilterEnabled)
        }

        bot.on<MessageCreateEvent> {
            if (message.author?.id?.asString != config.discord.ownerId) return@on
            if (config.filter == null) return@on

            if (swearFilterEnabled) {
                val swore = config.filter.swearing.list
                    .any { swear -> message.content.contains(swear, true) }

                if (swore) {
                    for (i in 0..Random.nextInt(1, 3)) {
                        val emoji = config.filter.swearing.reactions.random()
                        message.addReaction(ReactionEmoji.Unicode(emoji.removeSurrounding("<", ">")))
                    }

                    delay(config.filter.swearing.deleteDelay)
                    message.delete()
                }
            }
        }
    }

    private suspend fun EphemeralSlashCommand<*>.addFilterToggle(name: String, property: KMutableProperty<Boolean>) {
        ephemeralSubCommand(::FilterToggleArguments) {
            this@ephemeralSubCommand.name = name
            description = "Toggle the $name filter"

            action {
                if (arguments.enabled == null) {
                    respond { content = "The filter is currently ${property.getter.call().english}." }
                    return@action
                }

                property.setter.call(arguments.enabled)
                respond { content = "The filter is now ${arguments.enabled!!.english}." }
            }
        }
    }

    inner class FilterToggleArguments : Arguments() {
        val enabled: Boolean? by optionalBoolean("enabled", "Whether the filter should be enabled")
    }

    private val Boolean.english: String
        get() = if (this) "enabled" else "disabled"
}