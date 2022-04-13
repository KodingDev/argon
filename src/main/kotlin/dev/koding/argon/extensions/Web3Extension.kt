package dev.koding.argon.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.application.slash.publicSubCommand
import com.kotlindiscord.kord.extensions.commands.converters.impl.string
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import com.kotlindiscord.kord.extensions.types.respondingPaginator
import dev.koding.argon.util.*
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
                    val address = WalletNameManager.getAddressForName(arguments.address)?.first() ?: arguments.address
                    if (!address.isAddress()) discordError("Invalid wallet address")

                    val rewards = runCatching {
                        Contracts.heroGalaxyEscrowContract.checkUserRewards(address).request()
                    }.getOrElse { discordError("Invalid wallet address") } ?: discordError("Invalid wallet address")

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
                    val address = WalletNameManager.getAddressForName(arguments.address)?.first() ?: arguments.address
                    if (!address.isAddress()) discordError("Invalid wallet address")

                    val name = WalletNameManager.getNameForAddress(address)
                    if (name.equals(address, true)) discordError("No name found for wallet address")

                    respond { feedback("[`${address}`](${Web3.polygon.explorer!!.getAddress(address)}) resolves to name `$name`.") }
                }
            }

            publicSubCommand(::LookupArguments) {
                name = "reverse"
                description = "Lookup a wallet name's address"

                action {
                    val address = WalletNameManager.getAddressForName(arguments.address)
                    if (address == null || address.isEmpty()) discordError("No address found for wallet name")

                    respondingPaginator {
                        address.chunked(10).forEachIndexed { page, chunk ->
                            page {
                                title = "Addresses $DOUBLE_ARROW ${arguments.address}"
                                color = Colors.SUCCESS

                                description = chunk
                                    .mapIndexed { index, s ->
                                        "`${
                                            (page * 10 + index).toString().padStart(2)
                                        }` **|** [`${s}`](${Web3.polygon.explorer!!.getAddress(s)})"
                                    }.joinToString("\n")
                            }
                        }
                    }.send()
                }
            }

            publicSubCommand(::LookupArguments) {
                name = "balances"
                description = "Lookup WRLD balances for a wallet address"

                action {
                    val address = WalletNameManager.getAddressForName(arguments.address)
                    if (address == null || address.isEmpty()) discordError("No address found for wallet name")

                    val balances = address.mapThreaded(10) {
                        it to (Contracts.wrldContract.balanceOf(it).request()?.fromEther() ?: 0.0).toFixed(3)
                    }

                    respondingPaginator {
                        balances.chunked(10).forEachIndexed { page, chunk ->
                            page {
                                title = "Addresses $DOUBLE_ARROW ${arguments.address}"
                                color = Colors.SUCCESS

                                description = chunk
                                    .mapIndexed { index, (address, balance) ->
                                        val paddedPage = (page * 10 + index).toString().padStart(2)
                                        val paddedBalance = balance.padStart(7)
                                        "`$paddedPage` **|** [`${address}`](${Web3.polygon.explorer!!.getAddress(address)}) **|** `$paddedBalance WRLD`"
                                    }.joinToString("\n")
                            }
                        }
                    }.send()
                }
            }
        }
    }

    inner class LookupArguments : Arguments() {
        val address by string("address", "The address to lookup")
    }
}