package dev.koding.argon.util.web3.resolver

import dev.koding.argon.util.web3.Contracts
import dev.koding.argon.util.web3.Web3
import dev.koding.argon.util.web3.isAddress
import me.kbrewster.mojangapi.MojangAPI
import java.util.*

interface WalletNameResolver {
    fun resolve(address: String): String?

    fun reverse(name: String): List<String>? = null
}

// Use this in the future to resolve names from the NFTWorlds contract
@Suppress("unused")
object WalletNameManager {
    // Name -> Address
    private val nameCache = hashMapOf<String, List<String>?>()

    // Address -> Name
    private val addressCache = hashMapOf<String, String>()

    private val resolvers = arrayOf(MappingResolver, MinecraftResolver, ENSResolver)

    @JvmStatic
    fun getNameForAddress(address: String) = addressCache.getOrPut(address.lowercase()) {
        resolvers.asSequence().mapNotNull { it.resolve(address) }.firstOrNull() ?: address
    }

    @JvmStatic
    fun getAddressForName(name: String): List<String>? {
        nameCache[name.lowercase()]?.let { return it.takeIf { it.isNotEmpty() } }

        val addresses = resolvers.asSequence().mapNotNull { it.reverse(name) }.firstOrNull()
        nameCache[name.lowercase()] = addresses
        return addresses
    }
}

object ENSResolver : WalletNameResolver {
    override fun resolve(address: String) = runCatching { Web3.ens.reverseResolve(address) }.getOrNull()

    override fun reverse(name: String) = runCatching { Web3.ens.resolve(name) }.getOrNull()?.let { listOf(it) }
}

object MinecraftResolver : WalletNameResolver {
    override fun resolve(address: String): String? {
        val uuid = Contracts.nftWorldsPlayerContract.assignedWalletUUID(address).send()
            .takeIf { it.isNotBlank() }
            ?.let { UUID.fromString(MojangAPI.addDashes(it)) }
            ?: return null

        return runCatching { MojangAPI.getProfile(uuid).name }.getOrNull()
    }

    override fun reverse(name: String): List<String>? {
        val uuid = runCatching { MojangAPI.getUUID(name) }.getOrNull() ?: return null
        return runCatching {
            Contracts.nftWorldsPlayerContract.getPlayerPrimaryWallet(uuid.toString().replace("-", "")).send()
        }
            .getOrNull()
            ?.takeIf { it.isNotBlank() && it.isAddress() }
            ?.let { listOf(it) }
    }
}

object MappingResolver : WalletNameResolver {
    private val mappings = WalletMapping.load()

    override fun resolve(address: String) = mappings.get(address)?.name

    override fun reverse(name: String) = mappings.firstOrNull { it.name.equals(name, true) }?.addresses
}
