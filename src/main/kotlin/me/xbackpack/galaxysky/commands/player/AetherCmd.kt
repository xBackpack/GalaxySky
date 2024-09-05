package me.xbackpack.galaxysky.commands.player

import me.xbackpack.galaxysky.commands.commandTypes.TeleportCommand
import me.xbackpack.galaxysky.util.PluginPermission
import me.xbackpack.galaxysky.util.Worlds
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class AetherCmd : TeleportCommand {
    override val commandName = "aether"
    override val description = "Teleports the player to the aether"
    override val permission =
        PluginPermission("galaxysky.world.aether", Component.text("You unlock this at 1m blocks mined!").color(NamedTextColor.RED))

    override val location = Worlds.aetherSpawnLocation
}
