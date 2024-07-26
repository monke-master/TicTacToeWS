package data

import domain.models.Player
import org.lighthousegames.logging.logging

class GameLocalDataSourceImpl: GameLocalDataSource {
    private lateinit var player: Player

    override fun setPlayer(player: Player) {
        logging("Player").d { player.id }
        this.player = player
    }

    override fun getPlayer(): Player {
        return player
    }

}