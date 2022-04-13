package dev.koding.argon.util.web3

import org.web3j.crypto.Bip32ECKeyPair
import org.web3j.crypto.Bip32ECKeyPair.HARDENED_BIT
import org.web3j.crypto.Credentials
import org.web3j.crypto.MnemonicUtils

class HDWallet(private val mnemonic: String) {

    private val masterKeyPair by lazy {
        val seed = MnemonicUtils.generateSeed(mnemonic, null)
        Bip32ECKeyPair.generateKeyPair(seed)
    }

    private val rootKeyPair = Bip32ECKeyPair.deriveKeyPair(
        masterKeyPair,
        intArrayOf(44 or HARDENED_BIT, 60 or HARDENED_BIT, 0 or HARDENED_BIT, 0)
    )

    private fun derive(number: Int): Credentials =
        Credentials.create(Bip32ECKeyPair.deriveKeyPair(rootKeyPair, intArrayOf(number)))

    fun deriveArray(size: Int) = Array(size) { derive(it) }

}