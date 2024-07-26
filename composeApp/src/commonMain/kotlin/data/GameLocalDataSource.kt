package data

import domain.models.Player

interface GameLocalDataSource {

    fun setPlayer(player: Player)

    fun getPlayer(): Player
}