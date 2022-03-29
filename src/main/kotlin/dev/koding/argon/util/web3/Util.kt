package dev.koding.argon.util.web3

import kotlinx.coroutines.future.await
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.core.RemoteCall
import java.math.BigDecimal
import java.math.BigInteger


const val NULL_ADDRESS = "0x0000000000000000000000000000000000000000"

suspend fun <T> RemoteCall<T>.request(): T = sendAsync().await()

fun BigInteger.fromEther(decimals: Int = 18) = (BigDecimal(this).divide(BigDecimal.TEN.pow(decimals))).toDouble()
fun String.isAddress(allowNull: Boolean = false): Boolean {
    if (!allowNull && this == NULL_ADDRESS) return false
    return WalletUtils.isValidAddress(this)
}