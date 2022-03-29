package dev.koding.argon.util.web3.resolver

import dev.koding.argon.util.web3.Contracts
import dev.koding.argon.util.web3.Web3
import dev.koding.argon.util.web3.isAddress
import me.kbrewster.mojangapi.MojangAPI
import java.util.*

interface WalletNameResolver {
    fun resolve(address: String): String?

    fun reverse(name: String): String? = null
}

// Use this in the future to resolve names from the NFTWorlds contract
@Suppress("unused")
object WalletNameManager {
    // Name -> Address
    private val nameCache = hashMapOf<String, String?>()

    // Address -> Name
    private val addressCache = hashMapOf<String, String>()

    private val resolvers = arrayOf(MappingResolver, MinecraftResolver, ENSResolver)

    @JvmStatic
    fun getName(address: String) =
        addressCache.getOrPut(address.lowercase()) {
            val name = resolvers.asSequence().mapNotNull { it.resolve(address) }.firstOrNull() ?: address
            nameCache[name.lowercase()] = address
            name
        }

    @JvmStatic
    fun getAddress(name: String): String? {
        nameCache[name.lowercase()]?.let { return it.takeIf { it.isNotBlank() } }

        val address = resolvers.asSequence().mapNotNull { it.reverse(name) }.firstOrNull()
        nameCache[name.lowercase()] = address ?: ""
        return address
    }
}

object ENSResolver : WalletNameResolver {
    override fun resolve(address: String) = runCatching { Web3.ens.reverseResolve(address) }.getOrNull()

    override fun reverse(name: String) = runCatching { Web3.ens.resolve(name) }.getOrNull()
}

object MinecraftResolver : WalletNameResolver {
    override fun resolve(address: String): String? {
        val uuid = Contracts.nftWorldsPlayerContract.assignedWalletUUID(address).send()
            .takeIf { it.isNotBlank() }
            ?.let { UUID.fromString(MojangAPI.addDashes(it)) }
            ?: return null

        return runCatching { MojangAPI.getProfile(uuid).name }.getOrNull()
    }

    override fun reverse(name: String): String? {
        val uuid = runCatching { MojangAPI.getUUID(name) }.getOrNull() ?: return null
        return runCatching { Contracts.nftWorldsPlayerContract.getPlayerPrimaryWallet(uuid.toString().replace("-", "")).send() }
            .getOrNull()?.takeIf { it.isNotBlank() && it.isAddress() }
    }
}

object MappingResolver : WalletNameResolver {
    private val mappings = WalletMapping.load()

    override fun resolve(address: String) = mappings.get(address)?.name

    override fun reverse(name: String) = mappings.firstOrNull { it.name.equals(name, true) }?.addresses?.first()
}
