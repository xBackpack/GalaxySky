package me.xbackpack.galaxysky.commands.player

import me.xbackpack.galaxysky.commands.commandTypes.InventoryCommand
import org.bukkit.entity.Player

class RedeemCmd : InventoryCommand.Player.CheckHand {
    override val commandName = "redeem"
    override val description = "Redeems your held compressor"

    override fun getIds() = TODO()

    override fun funToRun(player: Player) = TODO()
}
