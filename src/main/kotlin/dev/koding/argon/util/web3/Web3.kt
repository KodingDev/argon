package dev.koding.argon.util.web3

import org.web3j.contracts.eip20.generated.ERC20
import org.web3j.ens.EnsResolver
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ReadonlyTransactionManager
import org.web3j.tx.gas.DefaultGasProvider

data class Web3(
    val endpoint: String,
    val explorer: Explorer? = null
) {

    companion object {
        @JvmStatic
        val ethereum = Web3(
            "https://eth-mainnet.alchemyapi.io/v2/BJKzGRUBi_0Gc37k55IDF0NIyEOExYsd",
            Explorer("https://etherscan.io")
        )

        @JvmStatic
        val polygon = Web3(
            "https://polygon-mainnet.g.alchemy.com/v2/ZZhQH1Yt3EHggV-mBfVGZTrIy9o5XT9M",
            Explorer("https://polygonscan.com")
        )

        @JvmStatic
        val gasProvider = DefaultGasProvider()

        @JvmStatic
        val ens by lazy { EnsResolver(ethereum.provider) }
    }

    val provider: Web3j by lazy { Web3j.build(HttpService(endpoint)) }
    val readOnly by lazy { ReadonlyTransactionManager(provider, "0x0000000000000000000000000000000000000000") }

    @Suppress("unused")
    fun getERC20(address: String): ERC20 = ERC20.load(address, provider, readOnly, gasProvider)
}

class Explorer(private val url: String) {
    fun getAddress(address: String) = "$url/address/$address"
}