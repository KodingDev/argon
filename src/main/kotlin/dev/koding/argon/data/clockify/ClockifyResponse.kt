package dev.koding.argon.data.clockify

import kotlinx.serialization.Serializable
import java.time.Duration
import java.time.Instant

@Serializable
data class Workspace(
    val id: String,
    val name: String,
    val memberships: List<Memberships>
)

@Serializable
data class Rate(
    val amount: Double,
    val currency: String
)

@Serializable
data class Memberships(
    val targetId: String,
    val userId: String,
    val hourlyRate: Rate?
)

@Serializable
data class User(
    val id: String,
    val name: String,
    val memberships: List<Memberships>
)

@Serializable
data class TimeEntry(
    val id: String,
    val projectId: String?,
    val description: String?,
    val timeInterval: TimeInterval
) {
    @Serializable
    data class TimeInterval(
        val start: String,
        val end: String?,
        val duration: String?
    ) {
        val asDuration: Duration
            get() =
                if (duration != null) Duration.parse(duration)
                else Duration.ofMillis(System.currentTimeMillis() - Instant.parse(start).epochSecond * 1000)
    }
}

@Serializable
data class Project(
    val id: String,
    val name: String,
    val hourlyRate: Rate?,
    val clientName: String?
)