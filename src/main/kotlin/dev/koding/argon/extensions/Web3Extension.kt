package dev.koding.argon.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.application.slash.publicSubCommand
import com.kotlindiscord.kord.extensions.commands.converters.impl.string
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import dev.koding.argon.util.discordError
import dev.koding.argon.util.feedback
import dev.koding.argon.util.web3.*
import dev.koding.argon.util.web3.resolver.WalletNameManager

class Web3Extension(override val name: String = "Web3") : Extension() {
    override suspend fun setup() {
        publicSlashCommand {
            name = "web3"
            description = "Lookup Web3 information"

            publicSubCommand(::LookupArguments) {
                name = "hgrewards"
                description = "Lookup Hero Galaxy staking rewards"

                action {
                    val address = WalletNameManager.getAddress(arguments.address) ?: arguments.address
                    if (!address.isAddress()) discordError("Invalid wallet address")

                    val rewards = runCatching {
                        Contracts.heroGalaxyEscrowContract.checkUserRewards(address).request()
                    }.getOrElse { discordError("Invalid wallet address") }

                    respond {
                        feedback {
                            title = "Hero Galaxy Rewards"
                            description =
                                "Staking rewards for [`$address`](${Web3.polygon.explorer!!.getAddress(address)})\n**${rewards.fromEther()} WRLD**"
                        }
                    }
                }
            }

            publicSubCommand(::LookupArguments) {
                name = "lookup"
                description = "Lookup a wallet address's name"

                action {
                    val address = WalletNameManager.getAddress(arguments.address) ?: arguments.address
                    if (!address.isAddress()) discordError("Invalid wallet address")

                    val name = WalletNameManager.getName(address)
                    if (name.equals(address, true)) discordError("No name found for wallet address")

                    respond { feedback("[`${address}`](${Web3.polygon.explorer!!.getAddress(address)}) resolves to name `$name`.") }
                }
            }

            publicSubCommand(::LookupArguments) {
                name = "reverse"
                description = "Lookup a wallet name's address"

                action {
                    val address = WalletNameManager.getAddress(arguments.address)
                    if (address == null || address.equals(
                            arguments.address,
                            true
                        )
                    ) discordError("No address found for wallet name")

                    respond {
                        feedback(
                            "`${arguments.address}` resolves to address [`${address}`](${
                                Web3.polygon.explorer!!.getAddress(
                                    address
                                )
                            })."
                        )
                    }
                }
            }
        }
    }

    inner class LookupArguments : Arguments() {
        val address by string("address", "The address to lookup")
    }
}