package me.xbackpack.galaxysky.commands.commandTypes

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

interface EntityMountCommand : CommandBase {
    override val requiresPlayer: Boolean
        get() = true

    fun createEntity(player: Player): LivingEntity

    fun mount(player: Player) {
        val entity = createEntity(player)

        entity.addPassenger(player)
    }
}
