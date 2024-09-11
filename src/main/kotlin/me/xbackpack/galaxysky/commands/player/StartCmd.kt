package me.xbackpack.galaxysky.commands.player

import me.xbackpack.galaxysky.commands.commandTypes.CooldownCommand
import me.xbackpack.galaxysky.commands.commandTypes.InventoryCommand
import me.xbackpack.galaxysky.util.Item
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import java.util.UUID

class StartCmd :
    InventoryCommand.Player.AddItem,
    CooldownCommand {
    override val requiresPlayer = true

    override val commandName = "start"
    override val description = "Gives the player the starter pickaxe"
    override val cooldownDuration = 1200L
    override val playersWithCooldown = hashSetOf<UUID>()

    override fun getItem(): Item =
        Item(Material.WOODEN_PICKAXE).apply {
            name =
                Component
                    .text()
                    .append(Component.text("Wooden Pickaxe").color(NamedTextColor.DARK_GRAY))
                    .appendSpace()
                    .append(Component.text("1").color(NamedTextColor.AQUA))
                    .build()
            isUnbreakable = true
        }
}
