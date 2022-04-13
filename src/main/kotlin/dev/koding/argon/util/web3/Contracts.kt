package dev.koding.argon.util.web3

import org.web3j.heroescrow.HeroEscrow
import org.web3j.nftworldsplayers.NFTWorldsPlayers

object Contracts {

    @JvmStatic
    val nftWorldsPlayerContract: NFTWorldsPlayers = NFTWorldsPlayers.load(
        "0xF405Fb60690395D8d4d047Cc8916Df256270285f",
        Web3.polygon.provider,
        Web3.polygon.readOnly,
        Web3.gasProvider
    )

    @JvmStatic
    val heroGalaxyEscrowContract: HeroEscrow = HeroEscrow.load(
        "0x3EC08e524B9eE38d66Eb5889229d092352482AAa",
        Web3.ethereum.provider,
        Web3.ethereum.readOnly,
        Web3.gasProvider
    )

    @JvmStatic
    val wrldContract = Web3.polygon.getERC20("0xD5d86FC8d5C0Ea1aC1Ac5Dfab6E529c9967a45E9")

}
