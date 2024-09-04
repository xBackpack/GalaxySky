package me.xbackpack.galaxysky.commands.player

import me.xbackpack.galaxysky.commands.commandTypes.EntityMountCommand
import me.xbackpack.galaxysky.commands.commandTypes.ListenerCommand
import me.xbackpack.galaxysky.util.PluginUtilities.content
import net.kyori.adventure.text.Component
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDismountEvent

class SitCmd :
    EntityMountCommand,
    ListenerCommand {
    override val commandName = "sit"
    override val description = "Makes the player sit at their location"

    override fun createEntity(player: Player): LivingEntity {
        val armourStand = player.world.spawn(player.location.add(0.0, 1.0, 0.0), ArmorStand::class.java)

        armourStand.isMarker = true
        armourStand.isInvisible = true
        armourStand.customName(Component.text("Sit"))

        return armourStand
    }

    @EventHandler
    fun onDismount(event: EntityDismountEvent) {
        val player = event.entity as? Player ?: return

        val dismounted = event.dismounted

        val name = dismounted.customName() ?: return

        if (name.content() == "Sit") {
            dismounted.remove()
            player.teleport(player.location.add(0.0, 1.0, 0.0))
        }
    }
}
