package domain.models

import kotlinx.serialization.Serializable

@Serializable
sealed class EndStatus {
    @Serializable
    data class Win(val player: Player): EndStatus()

    @Serializable
    data object Draw: EndStatus()
}