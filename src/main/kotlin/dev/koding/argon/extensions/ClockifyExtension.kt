package dev.koding.argon.extensions

import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respondPublic
import dev.koding.argon.data.clockify.ClockifyHelper
import dev.koding.argon.util.asCurrency
import dev.koding.argon.util.asMonthDisplay
import dev.koding.argon.util.formatElapsedTime
import dev.koding.argon.util.ownerOnly
import dev.kord.common.Color
import dev.kord.rest.builder.message.create.embed
import java.util.*

class ClockifyExtension(override val name: String = "Clockify") : Extension() {
    override suspend fun setup() {
        publicSlashCommand {
            name = "clockify"
            description = "Fetch Clockify data"
            ownerOnly()

            action {
                val report = ClockifyHelper.fetchMonthlyReport()
                    .filterValues { reports -> reports.any { it.price > 0 } }

                respondPublic {
                    embed {
                        title = "Clockify"
                        description = "Monthly report"
                        color = Color(0x29b6f6)

                        report.forEach { (client, data) ->
                            field {
                                val workloadSummary = if (data.size <= 5) {
                                    data.joinToString(separator = "\n") {
                                        "`${it.project}`: ${it.description} (${it.time.formatElapsedTime()})" +
                                                " - ${it.price.asCurrency()}"
                                    }
                                } else {
                                    val sample = data.sortedByDescending { it.time }.take(5)
                                    val remainingTime = data.sumOf { it.time } - sample.sumOf { it.time }
                                    val remainingCost = data.sumOf { it.price } - sample.sumOf { it.price }

                                    val header = sample
                                        .joinToString(separator = "\n") {
                                            "`${it.project}`: ${it.description} (${it.time.formatElapsedTime()})" +
                                                    " - ${it.price.asCurrency()}"
                                        }
                                    "$header\n...and ${data.size - 5} more (${remainingTime.formatElapsedTime()} - ${remainingCost.asCurrency()})"
                                }

                                val totalEarned = data.sumOf { it.price }.asCurrency()
                                val totalTime = data.sumOf { it.time }.formatElapsedTime()

                                name = client.takeIf { it.isNotBlank() } ?: "No client"
                                value = "$workloadSummary\nTotal: **$totalEarned** - **$totalTime**"
                            }
                        }

                        field {
                            val totalEarned = report.values.flatten().sumOf { it.price }.asCurrency()
                            val totalTime = report.values.flatten().sumOf { it.time }.formatElapsedTime()

                            name = "Total"
                            value = "**$totalEarned** from $totalTime in ${Date().asMonthDisplay()}"
                        }

                        footer {
                            text = "Data provided by Clockify"
                        }
                    }
                }
            }
        }
    }
}