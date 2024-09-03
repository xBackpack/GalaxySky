package me.xbackpack.galaxysky.commands.player

import me.xbackpack.galaxysky.commands.commandTypes.InventoryCheckCommand
import org.bukkit.entity.Player

class RedeemCmd : InventoryCheckCommand {
    override val commandName = "redeem"
    override val description = "Redeems your held compressor"

    override fun getIds() = TODO()

    override fun getItems(player: Player) = arrayOf(player.inventory.itemInMainHand)

    override fun funToRun(player: Player) = TODO()
}
