package me.xbackpack.galaxysky.commands.player

import me.xbackpack.galaxysky.commands.commandTypes.TeleportCommand
import me.xbackpack.galaxysky.util.PluginPermission
import me.xbackpack.galaxysky.util.Worlds
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class NetherCmd : TeleportCommand {
    override val commandName = "nether"
    override val description = "Teleports the player to the nether"
    override val permission =
        PluginPermission("galaxysky.world.nether", Component.text("You unlock this at 10k blocks mined!").color(NamedTextColor.RED))

    override val location = Worlds.netherSpawnLocation
}
