package domain

import domain.models.Cell
import domain.models.CellType

private val row = listOf(Cell(CellType.Cross), Cell(null), Cell(null))

val Field = listOf(row, row, row)