package me.xbackpack.galaxysky.command.impl

import me.xbackpack.galaxysky.command.api.CommandArgument
import me.xbackpack.galaxysky.command.api.CommandBuilder
import me.xbackpack.galaxysky.command.api.UserCooldown
import me.xbackpack.galaxysky.item.api.Item
import me.xbackpack.galaxysky.item.api.giveItem
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ItemSupplyCommand : BaseCommand {
    override lateinit var name: String
    override lateinit var description: String
    override var aliases = emptyList<String>()
    override var permission: String? = null
    override var cooldown: UserCooldown? = null
    override val builder: (CommandSender, List<CommandArgument>) -> Unit = { sender, args ->
        val player = sender as Player

        player.giveItem(item)
    }
    override val configuration: CommandBuilder.() -> Unit = { playerOnly() }

    lateinit var item: Item
}
