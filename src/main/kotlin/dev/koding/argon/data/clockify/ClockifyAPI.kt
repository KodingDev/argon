package dev.koding.argon.data.clockify

import dev.koding.argon.data.config
import dev.koding.argon.util.getEndOfMonth
import dev.koding.argon.util.getStartOfMonth
import dev.koding.argon.util.json
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import java.util.*

private const val BASE_URL = "https://api.clockify.me/api/v1"

object ClockifyAPI {

    private val client = HttpClient {
        defaultRequest {
            header("X-Api-Key", config.clockify?.apiKey)
            url.takeFrom(URLBuilder().takeFrom(BASE_URL).apply { encodedPath += url.encodedPath })
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
    }

    suspend fun getWorkspaces(): List<Workspace> = client.get { url { path("workspaces") } }

    suspend fun getUser(): User = client.get { url { path("user") } }

    suspend fun getProjects(workspaceId: String): List<Project> =
        client.get { url { path("workspaces/$workspaceId/projects") } }

    suspend fun getTimeEntries(
        workspaceId: String,
        userId: String,
        start: Date? = null,
        end: Date? = null
    ): List<TimeEntry> = client.get {
        url { path("workspaces", workspaceId, "user", userId, "time-entries") }
        start?.let { parameter("start", it.toInstant().toString()) }
        end?.let { parameter("end", it.toInstant().toString()) }
    }

}

object ClockifyHelper {
    suspend fun fetchMonthlyReport(): Map<String, List<ReportData>> {
        val user = ClockifyAPI.getUser()
        val workspaces = ClockifyAPI.getWorkspaces()

        val data = workspaces.flatMap { workspace ->
            val defaultRate =
                workspace.memberships.find { it.userId == user.id && it.targetId == workspace.id }?.hourlyRate?.amount
                    ?: 0.0
            val projects = ClockifyAPI.getProjects(workspace.id)
            val timeEntries = ClockifyAPI.getTimeEntries(
                workspace.id,
                user.id,
                start = getStartOfMonth(),
                end = getEndOfMonth()
            )

            timeEntries.map { entry ->
                val project = entry.projectId?.let { id -> projects.find { p -> p.id == id } }
                val price = (project?.hourlyRate?.amount?.takeIf { it > 0.0 } ?: defaultRate) / 100.0 *
                        (entry.timeInterval.asDuration.toMinutes() / 60.0)

                ReportData(
                    project?.name ?: "Unknown",
                    project?.clientName ?: "Unknown",
                    entry.description ?: "",
                    price,
                    entry.timeInterval.asDuration.toMillis()
                )
            }
        }

        return data.groupBy { "${it.client}-${it.project}-${it.description}" }
            .map { (_, data) ->
                data.first().copy(
                    price = data.sumOf { it.price },
                    time = data.sumOf { it.time }
                )
            }
            .groupBy { it.client }
    }

}

@Serializable
data class ReportData(
    val project: String,
    val client: String,
    val description: String,
    val price: Double,
    val time: Long
)