package dev.koding.argon.util.web3.resolver

import dev.koding.argon.data.config
import dev.koding.argon.util.httpClient
import dev.koding.argon.util.json
import dev.koding.argon.util.web3.HDWallet
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import kotlin.random.Random

typealias WalletMappings = List<WalletMapping>

@Serializable
data class WalletMapping(
    val name: String,
    var address: JsonElement,
    val type: MappingType = MappingType.ADDRESS,
    val meta: Meta = Meta()
) {
    val addresses: List<String>
        get() = (address as? JsonArray)?.map { it.jsonPrimitive.content } ?: listOf(address.jsonPrimitive.content)

    @Serializable
    data class Meta(
        val ip: String? = null
    )

    companion object {
        private var instance: WalletMappings? = null

        lateinit var configMappings: WalletMappings

        @JvmStatic
        fun load(): WalletMappings {
            if (instance != null) return instance!!

            val fetchedMappings = try {
                runBlocking<WalletMappings> {
                    httpClient.get<String>(
                        "https://gist.githubusercontent.com/KodingDev/7d814a3fe38e512f59c321d254b308aa/raw/wallets.json?q=${
                            Random.nextInt(
                                Int.MAX_VALUE
                            )
                        }"
                    ).let { json.decodeFromString(it) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }

            configMappings = config.web3?.mappings ?: emptyList()
            val mappings = fetchedMappings + configMappings
            mappings.forEach { it.init() }
            return mappings.also { instance = it }
        }
    }

    fun init() {
        when (type) {
            MappingType.MNEMONIC -> {
                val wallet = HDWallet(address.jsonPrimitive.content)
                address = buildJsonArray {
                    wallet.deriveArray(1000).forEach { add(it.address) }
                }
            }
            else -> {}
        }
    }

    enum class MappingType {
        ADDRESS,
        MNEMONIC,
    }
}

fun WalletMappings.get(address: String) = firstOrNull { mapping -> mapping.addresses.any { it.equals(address, true) } }
