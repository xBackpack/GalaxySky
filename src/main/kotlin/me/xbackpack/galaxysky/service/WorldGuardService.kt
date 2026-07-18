package me.xbackpack.galaxysky.service

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import com.sk89q.worldguard.protection.ApplicableRegionSet
import com.sk89q.worldguard.protection.flags.Flags
import com.sk89q.worldguard.protection.flags.StateFlag
import com.sk89q.worldguard.protection.regions.ProtectedRegion
import me.xbackpack.galaxysky.api.util.giveItem
import me.xbackpack.galaxysky.enum.block.BlockType
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityShootBowEvent
import kotlin.random.Random

object WorldGuardService {
    private val container = WorldGuard.getInstance().platform.regionContainer

    fun Location.getFlag(
        bukkitPlayer: Player,
        flag: StateFlag,
    ): Boolean {
        val player = WorldGuardPlugin.inst().wrapPlayer(bukkitPlayer)
        val location = BukkitAdapter.adapt(this)
        val query = container.createQuery()

        return query.testState(location, player, flag)
    }

    fun World.getRegion(id: String): ProtectedRegion? {
        val world = BukkitAdapter.adapt(this)

        val regionContainer = container[world] ?: error("Couldn't find world named ${world.name}")

        return regionContainer.getRegion(id)
    }

    fun ProtectedRegion.fill(
        world: World,
        type: BlockType,
    ) {
        val editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(world))

        editSession.use {
            for (x in minimumPoint.x()..maximumPoint.x()) {
                for (y in minimumPoint.y()..maximumPoint.y()) {
                    for (z in minimumPoint.z()..maximumPoint.z()) {
                        val blockType = if (Random.nextDouble() < 0.9) type.mainType?.defaultState else type.rarerType?.defaultState
                        editSession.setBlock(BlockVector3.at(x, y, z), blockType)
                    }
                }
            }
        }
    }

    fun Location.getOccupiedRegions(): ApplicableRegionSet {
        val location = BukkitAdapter.adapt(this)

        return container.createQuery().getApplicableRegions(location)
    }

    fun ProtectedRegion.isInsideRegion(player: Player) = contains(BlockVector3.at(player.x, player.y, player.z))

    fun onBowShot(event: EntityShootBowEvent) {
        val projectile = event.projectile

        if (projectile.type != EntityType.ARROW) return

        val player = event.entity as? Player ?: return

        val pvpEnabled = player.location.getFlag(player, Flags.PVP)

        if (pvpEnabled) return

        event.isCancelled = true

        player.giveItem(projectile.pickItemStack)
    }
}
