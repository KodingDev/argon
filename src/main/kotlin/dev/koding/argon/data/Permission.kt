package dev.koding.argon.data

import com.kotlindiscord.kord.extensions.checks.userFor
import com.kotlindiscord.kord.extensions.commands.application.slash.SlashCommand
import dev.koding.argon.owners
import dev.kord.core.behavior.UserBehavior

enum class PermissionLevel {
    DEFAULT,
    WHITELISTED,
    BOT_TEAM;
}

val UserBehavior.permissionLevel: PermissionLevel
    get() = when {
        id in owners -> PermissionLevel.BOT_TEAM
        config.discord.whitelist.any { it == id.asString } -> PermissionLevel.WHITELISTED
        else -> PermissionLevel.DEFAULT
    }


fun SlashCommand<*, *>.permission(level: PermissionLevel) {
    check {
        failIf { (userFor(event)?.permissionLevel ?: PermissionLevel.DEFAULT) < level }
    }
}