package domain.models

import domain.models.CellType
import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: String,
    val type: CellType
)