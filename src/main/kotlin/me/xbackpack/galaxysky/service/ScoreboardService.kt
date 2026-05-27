package me.xbackpack.galaxysky.service

import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.enum.player.PlayerStatType
import me.xbackpack.galaxysky.hook.PlaceholderHook
import me.xbackpack.galaxysky.service.FormattingService.shortenStat
import me.xbackpack.galaxysky.service.FormattingService.shortenTime
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team
import java.util.UUID

object ScoreboardService {
    private val manager = Bukkit.getScoreboardManager()

    private const val OBJECTIVE_NAME = "GalaxySky"

    private data class PlayerBoard(
        val scoreboard: Scoreboard,
        val objective: Objective,
        val teams: Map<Int, Team>,
        val cache: MutableMap<Int, Message>,
    )

    private val boards = mutableMapOf<UUID, PlayerBoard>()

    private val entries =
        mapOf(
            1 to "§1",
            2 to "§2",
            3 to "§3",
            4 to "§4",
            5 to "§5",
            6 to "§6",
            7 to "§7",
            8 to "§8",
            9 to "§9",
        )

    fun create(player: Player) {
        if (boards.containsKey(player.uniqueId)) return

        val scoreboard = manager.newScoreboard

        val objective =
            scoreboard.registerNewObjective(
                OBJECTIVE_NAME,
                Criteria.DUMMY,
                Message
                    .create {
                        textGradient(
                            "GalaxySky",
                            "#FF00FF",
                            "#0000FF",
                            TextDecoration.BOLD,
                        )
                    }.component,
            )

        objective.displaySlot = DisplaySlot.SIDEBAR

        val teams = mutableMapOf<Int, Team>()

        entries.forEach { (score, entry) ->
            scoreboard.getObjective(OBJECTIVE_NAME)

            val team =
                scoreboard.registerNewTeam("line_$score").apply {
                    addEntry(entry)
                }

            objective.getScore(entry).score = score
            teams[score] = team
        }

        player.scoreboard = scoreboard

        val playerBoard =
            PlayerBoard(
                scoreboard = scoreboard,
                objective = objective,
                teams = teams,
                cache = mutableMapOf(),
            )

        boards[player.uniqueId] = playerBoard

        updatePlayer(player, playerBoard)
    }

    fun remove(player: Player) {
        boards.remove(player.uniqueId)
    }

    fun updateAll() {
        Bukkit.getOnlinePlayers().forEach { player ->
            boards[player.uniqueId]?.let {
                updatePlayer(player, it)
            }
        }
    }

    // =========================================================
    // PER PLAYER UPDATE
    // =========================================================

    private fun updatePlayer(
        player: Player,
        board: PlayerBoard,
    ) {
        setLine(board, 9, Message.space())

        setLine(
            board,
            8,
            Message.create {
                text("Rank") {
                    colour(NamedTextColor.WHITE)
                    bold()
                }
                text(": ")
                componentFromLegacyString(LuckPermsService.getPrefix(player))
            },
        )

        setLine(board, 7, Message.space())

        updateStat(board, player, 6, "Kills", PlayerStatType.KILLS, ::shortenStat)
        updateStat(board, player, 5, "Deaths", PlayerStatType.DEATHS, ::shortenStat)
        updateStat(board, player, 4, "Blocks Mined", PlayerStatType.BLOCKS_MINED, ::shortenStat)
        updateStat(board, player, 3, "Playtime", PlayerStatType.PLAYTIME, ::shortenTime)

        setLine(board, 2, Message.space())
        setLine(
            board,
            1,
            Message.create {
                text(PlaceholderHook.SERVER_IP) {
                    colour(NamedTextColor.YELLOW)
                }
            },
        )
    }

    private fun updateStat(
        board: PlayerBoard,
        player: Player,
        idx: Int,
        name: String,
        type: PlayerStatType,
        formatter: (Int) -> String,
    ) {
        val stat = PDCService.PlayerData.Stats[player, type]

        setLine(
            board,
            idx,
            Message
                .create {
                    text(name) {
                        colour(NamedTextColor.WHITE)
                        bold()
                    }
                    text(": ")
                    text(formatter(stat)) { colour(NamedTextColor.AQUA) }
                },
        )
    }

    private fun setLine(
        board: PlayerBoard,
        idx: Int,
        message: Message,
    ) {
        val old = board.cache[idx]

        if (old == message) {
            return
        }

        board.cache[idx] = message

        board.teams[idx]?.prefix(message.component)
    }
}
