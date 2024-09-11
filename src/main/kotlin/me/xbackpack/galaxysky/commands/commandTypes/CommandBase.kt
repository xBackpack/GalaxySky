package me.xbackpack.galaxysky.commands.commandTypes

import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import me.xbackpack.galaxysky.util.PluginPermission
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

interface CommandBase : BasicCommand {
    val requiresPlayer: Boolean
        get() = false

    val commandName: String
    val description: String
    val aliases: List<String>
        get() = emptyList()
    val permission: PluginPermission?
        get() = null

    fun command(
        sender: CommandSender,
        args: List<String>,
    ) {
        permission?.let {
            if (!(sender.hasPermission(it.permission) || sender.isOp)) {
                sender.sendMessage(it.message)
                return
            }
        }

        if (this is MessageCommand) sendMessage(sender)
        if (this is StaffCommand) handle(sender, args)

        if (requiresPlayer) {
            val player = sender as Player

            if (this is TeleportCommand) teleport(player)
            if (this is MessageCommand.Player) sendMessage(player)
            if (this is ToggleableCommand) toggleFeature(player)
            if (this is InventoryCommand.Create) openInventory(player)
            if (this is InventoryCommand.Player.AddItem) addItem(player)
            if (this is InventoryCommand.Player.CheckHand) checkItems(player)
            if (this is EntityMountCommand) mount(player)
            if (this is CooldownCommand) startCooldown(player)
        }
    }

    fun tabComplete(
        sender: CommandSender,
        args: Array<String>,
    ) = emptyList<String>()

    override fun suggest(
        cmd: CommandSourceStack,
        args: Array<String>,
    ) = tabComplete(cmd.sender, args)

    override fun execute(
        cmd: CommandSourceStack,
        args: Array<String>,
    ) {
        val sender = cmd.sender

        if (requiresPlayer && sender !is Player) {
            sender.sendMessage("Only players can execute this command")
            return
        }

        command(sender, args.toList())
    }
}
