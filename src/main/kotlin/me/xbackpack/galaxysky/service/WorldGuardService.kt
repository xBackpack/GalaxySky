package me.xbackpack.galaxysky.service

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import com.sk89q.worldguard.protection.ApplicableRegionSet
import com.sk89q.worldguard.protection.flags.StateFlag
import com.sk89q.worldguard.protection.regions.ProtectedRegion
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player

object WorldGuardService {
    private val container = WorldGuard.getInstance().platform.regionContainer

    fun getFlag(
        bukkitPlayer: Player,
        bukkitLocation: Location,
        flag: StateFlag,
    ): Boolean {
        val player = WorldGuardPlugin.inst().wrapPlayer(bukkitPlayer)
        val location = BukkitAdapter.adapt(bukkitLocation)
        val query = container.createQuery()

        return query.testState(location, player, flag)
    }

    fun getRegion(
        bukkitWorld: World,
        id: String,
    ): ProtectedRegion? {
        val world = BukkitAdapter.adapt(bukkitWorld)

        val regionContainer = container[world] ?: error("Couldn't find world named ${world.name}")

        return regionContainer.getRegion(id)
    }

    fun getOccupiedRegions(bukkitLocation: Location): ApplicableRegionSet {
        val location = BukkitAdapter.adapt(bukkitLocation)

        return container.createQuery().getApplicableRegions(location)
    }
}
