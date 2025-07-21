package me.xbackpack.galaxysky.command.impl

import me.xbackpack.galaxysky.command.api.CommandArgument
import me.xbackpack.galaxysky.command.api.CommandBuilder
import me.xbackpack.galaxysky.command.api.UserCooldown
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TeleportCommand : BaseCommand {
    override lateinit var name: String
    override lateinit var description: String
    override var aliases: List<String> = emptyList()
    override var permission: String? = null
    override var cooldown: UserCooldown? = null
    override val builder: (CommandSender, List<CommandArgument>) -> Unit = { sender, _ ->
        val player = sender as Player

        player.teleport(location)
    }
    override val configuration: CommandBuilder.() -> Unit = {
        playerOnly()
        staffCanUseOnOthers()
    }

    lateinit var location: Location
}
