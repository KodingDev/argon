package dev.koding.argon.util.web3.resolver

import dev.koding.argon.util.httpClient
import dev.koding.argon.util.json
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonPrimitive
import kotlin.random.Random

typealias WalletMappings = List<WalletMapping>

@Serializable
data class WalletMapping(
    val name: String,
    val address: JsonElement,
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

        @JvmStatic
        fun load(): WalletMappings {
            if (instance != null) return instance!!
            return try {
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
            }.also { instance = it }
        }
    }
}

fun WalletMappings.get(address: String) = firstOrNull { mapping -> mapping.addresses.any { it.equals(address, true) } }
