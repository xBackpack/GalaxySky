package me.xbackpack.galaxysky.api.command.types

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.tree.CommandNode
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import me.xbackpack.galaxysky.api.command.Cooldown
import me.xbackpack.galaxysky.api.command.Permission
import me.xbackpack.galaxysky.api.command.Requirement
import me.xbackpack.galaxysky.api.command.branches.OptionalArgument
import me.xbackpack.galaxysky.api.command.branches.RequiredArgument
import me.xbackpack.galaxysky.api.command.branches.Subcommand

interface Branch : Executable {
    val cooldown: Cooldown?

    val arguments: MutableList<RequiredArgument>
    var optional: OptionalArgument?
    val subcommands: MutableList<Subcommand>

    fun argument(
        name: String,
        type: ArgumentType<*>,
    ) {
        arguments += RequiredArgument(name, type)
    }

    fun optional(
        name: String,
        type: ArgumentType<*>,
        requirement: Requirement = this.requirement,
        permission: Permission? = null,
        factory: OptionalArgument.() -> Unit,
    ) {
        optional = OptionalArgument(name, type, requirement, permission, this).apply(factory)
    }

    fun subcommand(
        name: String,
        requirement: Requirement = this.requirement,
        permission: Permission? = null,
        cooldown: Cooldown? = null,
        factory: Subcommand.() -> Unit,
    ) {
        subcommands += Subcommand(name, requirement, permission, cooldown, this).apply(factory)
    }

    fun build(): LiteralCommandNode<CommandSourceStack> {
        var rootNode = Commands.literal(name)

        subcommands.forEach { subcommand ->
            rootNode = rootNode.then(subcommand.build())
        }

        var argNode: CommandNode<CommandSourceStack>

        if (arguments.isNotEmpty()) {
            val reversedArgs = arguments.reversed().toMutableList()

            argNode =
                reversedArgs
                    .removeFirst()
                    .build()
                    .buildTo(cooldown, arguments)
                    .apply { optional?.let { then(it.build()) } }
                    .build()

            reversedArgs.forEach { arg ->
                argNode = arg.build().then(argNode).build()
            }

            rootNode = rootNode.then(argNode)
        } else {
            rootNode =
                rootNode
                    .buildTo(cooldown, arguments)
                    .apply { optional?.let { then(it.build()) } }
        }

        return rootNode.build()
    }
}
