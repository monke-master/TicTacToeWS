package ui.game

import domain.models.CellType
import domain.models.EndStatus

enum class ViewEndGameStatus {
    Win, Defeat, Draw
}

fun EndStatus.toViewStatus(type: CellType): ViewEndGameStatus {
    return when (this) {
        EndStatus.Draw -> ViewEndGameStatus.Draw
        is EndStatus.Win -> if (type == cellType) ViewEndGameStatus.Win else ViewEndGameStatus.Defeat
    }
}