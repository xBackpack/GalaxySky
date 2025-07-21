package me.xbackpack.galaxysky.item.registry

import me.xbackpack.galaxysky.common.Registry

object Pickaxes : Registry {
    override fun init() {}

    val STONE_PICKAXE_1 = Items.getPickaxe("stone_pickaxe_1")

    val ADMIN_PICKAXE = Items.getPickaxe("admin_pickaxe")
}
