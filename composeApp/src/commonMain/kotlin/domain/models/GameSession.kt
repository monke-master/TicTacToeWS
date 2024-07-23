package domain.models

import kotlinx.serialization.Serializable

@Serializable
data class GameSession(
    val players: List<Player>,
    val game: Game,
    val code: String
)
