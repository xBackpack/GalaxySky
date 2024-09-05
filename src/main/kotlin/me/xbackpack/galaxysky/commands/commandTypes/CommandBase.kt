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

        if (requiresPlayer) {
            val player = sender as Player

            when (this) {
                is CooldownCommand -> {
                    if (checkCooldown(player)) return
                    startCooldown(player)
                }
                is TeleportCommand -> teleport(player)
                is PlayerMessageCommand -> sendMessage(player)
                is ToggleableCommand -> toggleFeature(player)
                is InventoryCommand.Create -> openInventory(player)
                is InventoryCommand.Player.AddItem -> addItem(player)
                is InventoryCommand.Player.CheckHand -> checkItems(player)
                is EntityMountCommand -> mount(player)
            }
        }

        when (this) {
            is MessageCommand -> sendMessage(sender)
            is StaffCommand -> handle(sender, args)
        }
    }

    override fun suggest(
        cmd: CommandSourceStack,
        args: Array<String>,
    ): List<String> = emptyList()

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
