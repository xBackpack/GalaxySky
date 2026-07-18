package me.xbackpack.galaxysky.service

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType

object LocationService {
    val WORLD: World = Bukkit.getWorld("world") ?: error("Cannot find world")
    val NETHER: World = Bukkit.getWorld("world_nether") ?: error("Cannot find world_nether")
    val END: World = Bukkit.getWorld("world_the_end") ?: error("Cannot find world_the_end")
    val AETHER: World = Bukkit.createWorld(WorldCreator("world_aether")) ?: error("Cannot find world_aether")
    private val STAFF_WORLD: World =
        Bukkit.createWorld(
            WorldCreator("world_staff")
                .type(WorldType.FLAT)
                .generateStructures(false),
        ) ?: error("Cannot find world_staff")

    operator fun get(name: String) =
        when (name) {
            "world" -> WORLD
            "nether" -> NETHER
            "end" -> END
            "aether" -> AETHER
            "staff" -> STAFF_WORLD
            else -> null
        }

    val WORLD_SPAWN = Location(WORLD, 0.5, 102.0, 0.5, 0f, 0f)

    val NETHER_SPAWN = Location(NETHER, -6.5, 12.0, -0.5, 180f, 0f)

    val END_SPAWN = Location(END, 0.5, 62.0, 0.5, 180f, 0f)

    val AETHER_SPAWN = Location(AETHER, 0.5, 100.0, 0.5, 180f, 0f)

    val STAFF_WORLD_SPAWN = Location(STAFF_WORLD, 21.5, -59.0, -31.5, 90f, 0f)

    val STAFF_AREA = Location(WORLD, 17.5, 101.0, -23.5, 90f, 0f)

    val BUILDER_AREA = Location(WORLD, -7.5, 101.0, -35.5, 90f, 0f)

    val AFK = Location(WORLD, -28.5, 102.0, 10.5, 90f, 0f)

    val LEADERBOARDS = Location(WORLD, 44.5, 102.0, 9.5, -45f, 0f)

    val FORGE = Location(NETHER, 15.0, 12.0, 1.0)
}
