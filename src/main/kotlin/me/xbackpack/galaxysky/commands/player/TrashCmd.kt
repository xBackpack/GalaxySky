package me.xbackpack.galaxysky.commands.player

import me.xbackpack.galaxysky.commands.commandTypes.InventoryCommand
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class TrashCmd : InventoryCommand.Create {
    override val commandName = "trash"
    override val description = "Opens a trash menu"
    override val aliases = arrayListOf("bin", "disposal")

    override fun getInventory(player: Player) = Bukkit.createInventory(null, 27, Component.text("Trash"))
}
