package me.xbackpack.galaxysky.service

import me.xbackpack.galaxysky.enum.player.PlayerStatType
import me.xbackpack.galaxysky.hook.PlaceholderHook
import me.xbackpack.galaxysky.message.Message
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot

object ScoreboardService {
    private val manager = Bukkit.getScoreboardManager()
    private val mainScoreboard = manager.newScoreboard
    private val objective =
        mainScoreboard.registerNewObjective(
            "GalaxySky",
            Criteria.DUMMY,
            Message
                .create {
                    textGradient("GalaxySky", "#FF00FF", "#0000FF") {
                        bold()
                    }
                }.root,
        )

    fun setupScoreboard() {
        objective.displaySlot = DisplaySlot.SIDEBAR

        setupScore(1, Message.create { text(PlaceholderHook.SERVER_IP) { colour(NamedTextColor.YELLOW) } })

        setupScore(2, Message.empty())

        setupScore(7, Message.empty())

        setupScore(9, Message.empty())
    }

    fun update() {
        Bukkit.getOnlinePlayers().forEach(::updatePlayer)
    }

    private fun updatePlayer(player: Player) {
        updateStat(player, 3, "Playtime", PlayerStatType.PLAYTIME)
        updateStat(player, 4, "Blocks Mined", PlayerStatType.BLOCKS_MINED)
        updateStat(player, 5, "Deaths", PlayerStatType.DEATHS)
        updateStat(player, 6, "Kills", PlayerStatType.KILLS)

        setupScore(
            8,
            Message.create {
                text("Rank") {
                    colour(NamedTextColor.WHITE)
                    bold()
                }

                text(":")

                space()

                componentFromLegacyString(LuckPermsService.getPrefix(player))
            },
        )

        player.scoreboard = mainScoreboard
    }

    private fun updateStat(
        player: Player,
        idx: Int,
        name: String,
        type: PlayerStatType,
    ) {
        setupScore(
            idx,
            Message.create {
                text(name) {
                    colour(NamedTextColor.WHITE)
                    bold()
                }

                text(":")

                space()

                val stat = PDCService.Player.Stats[player, type]

                text(stat.toString()) {
                    colour(NamedTextColor.AQUA)
                }
            },
        )
    }

    private fun setupScore(
        idx: Int,
        message: Message,
    ) {
        objective.getScore(idx.toString()).apply {
            customName(message.root)
            score = idx
        }
    }
}
