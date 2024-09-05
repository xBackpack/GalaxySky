package me.xbackpack.galaxysky.util

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType

object Worlds {
    val world: World = Bukkit.getWorld("world")!!
    private val nether: World = Bukkit.getWorld("world_nether")!!
    private val end: World = Bukkit.getWorld("world_the_end")!!
    private val aether: World = Bukkit.createWorld(WorldCreator("world_aether"))!!
    private val staff: World = Bukkit.createWorld(WorldCreator("world_staff").type(WorldType.FLAT).generateStructures(false))!!

    val spawnLocation: Location
        get() = Location(world, 0.5, 102.0, 0.5, 0f, 0f)

    val netherSpawnLocation: Location
        get() = Location(nether, -6.5, 12.0, -0.5, 180f, 0f)

    val endSpawnLocation: Location
        get() = Location(end, 0.5, 62.0, 0.5, 180f, 0f)

    val aetherSpawnLocation: Location
        get() = Location(aether, 0.5, 100.0, 0.5, 180f, 0f)

    val staffWorldSpawnLocation: Location
        get() = Location(staff, 0.0, -60.0, 0.0, 0f, 0f)
}
