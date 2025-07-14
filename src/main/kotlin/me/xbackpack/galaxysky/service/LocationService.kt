package me.xbackpack.galaxysky.service

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType

object LocationService {
    private val world: World = Bukkit.getWorld("world") ?: error("Cannot find world")
    private val nether: World = Bukkit.getWorld("world_nether") ?: error("Cannot find world_nether")
    private val end: World = Bukkit.getWorld("world_the_end") ?: error("Cannot find world_the_end")
    private val aether: World = Bukkit.createWorld(WorldCreator("world_aether")) ?: error("Cannot find world_aether")
    private val staff: World =
        Bukkit.createWorld(
            WorldCreator("world_staff")
                .type(WorldType.FLAT)
                .generateStructures(false),
        ) ?: error("Cannot find world_staff")

    val spawnLocation = Location(world, 0.5, 102.0, 0.5, 0f, 0f)

    val netherSpawnLocation = Location(nether, -6.5, 12.0, -0.5, 180f, 0f)

    val endSpawnLocation = Location(end, 0.5, 62.0, 0.5, 180f, 0f)

    val aetherSpawnLocation = Location(aether, 0.5, 100.0, 0.5, 180f, 0f)

    val staffWorldSpawnLocation = Location(staff, 21.5, -59.0, -31.5, 90f, 0f)

    val staffAreaLocation = Location(world, 17.5, 101.0, -23.5, 90f, 0f)

    val builderAreaLocation = Location(world, -7.5, 101.0, -35.5, 90f, 0f)

    val afkLocation = Location(world, -28.5, 102.0, 10.5, 90f, 0f)

    fun init() {}
}
