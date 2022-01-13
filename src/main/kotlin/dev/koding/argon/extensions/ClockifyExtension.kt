package dev.koding.argon.extensions

import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respondPublic
import dev.koding.argon.data.clockify.ClockifyHelper
import dev.koding.argon.util.asMonthDisplay
import dev.koding.argon.util.formatElapsedTime
import dev.koding.argon.util.round
import dev.kord.common.Color
import dev.kord.rest.builder.message.create.embed
import java.util.*

class ClockifyExtension(override val name: String = "Clockify") : Extension() {
    override suspend fun setup() {
        publicSlashCommand {
            name = "clockify"
            description = "Fetch Clockify data"

            action {
                val report = ClockifyHelper.fetchMonthlyReport()

                respondPublic {
                    embed {
                        title = "Clockify"
                        description = "Monthly report"
                        color = Color(0x29b6f6)

                        report.forEach { (client, data) ->
                            field {
                                val workloadSummary = data.joinToString(separator = "\n") {
                                    "`${it.project}`: ${it.description} (${it.time.formatElapsedTime()})" +
                                            " - $${it.price.round(2)} USD"
                                }
                                val totalEarned = data.sumOf { it.price }.round(2)
                                val totalTime = data.sumOf { it.time }.formatElapsedTime()

                                name = client
                                value = "$workloadSummary\nTotal: **$$totalEarned USD** - **$totalTime**"
                            }
                        }

                        field {
                            name = "Total"
                            value =
                                "**$${
                                    report.values.flatten().sumOf { it.price }.round(2)
                                } USD** in ${Date().asMonthDisplay()}"
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