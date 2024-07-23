package domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val field: List<List<Cell>>,
    val gameStatus: GameStatus,
    val turn: Turn,
)
