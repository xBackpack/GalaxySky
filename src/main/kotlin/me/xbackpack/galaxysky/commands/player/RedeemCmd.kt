package me.xbackpack.galaxysky.commands.player

import me.xbackpack.galaxysky.commands.commandTypes.PlayerInventoryCommand
import org.bukkit.entity.Player

class RedeemCmd : PlayerInventoryCommand.CheckHand {
    override val commandName = "redeem"
    override val description = "Redeems your held compressor"

    override fun getIds() = TODO()

    override fun funToRun(player: Player) = TODO()
}
