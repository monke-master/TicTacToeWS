package ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.models.Cell
import domain.models.CellType
import domain.Field
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.ic_cross
import tictactoe.composeapp.generated.resources.ic_nought
import ui.composable.GradientBackground
import ui.composable.VerticalDivider
import ui.theme.Grey

@Composable
fun GameScreen() {
    GradientBackground {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Timer(
                modifier = Modifier.padding(top = 72.dp),
                time = "0:05"
            )
            TurnInfo(
                modifier = Modifier.padding(top = 24.dp),
                turnInfo = "Player 1’s Turn"
            )
            GameGrid(Field.flatten(), Field.size)
        }
    }
    
}

@Composable
private fun Timer(
    time: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(100.dp)
    ) {
        Text(
            text = time,
            fontSize = 34.sp,
            modifier = Modifier.padding(
                horizontal = 32.dp,
                vertical = 8.dp
            )
        )
    }
}

@Composable
private fun TurnInfo(
    modifier: Modifier = Modifier,
    turnInfo: String
) {
    Text(
        text = turnInfo,
        modifier = modifier,
        fontSize = 36.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun GameGrid(
    cells: List<Cell>,
    columns: Int
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 28.dp)
    ) {
        LazyVerticalGrid(
            modifier = Modifier.padding(28.dp).fillMaxWidth(),
            columns = GridCells.Fixed(columns)
        ) {
            items(cells.size) { index ->
                CellItem(
                    cell = cells[index],
                    index = index,
                    onClicked = {index -> },
                    showHorizontalDivider = index / columns < columns - 1,
                    showVerticalDivider =  (index + 1) % columns != 0,
                )
            }
        }
    }
}

@Composable
private fun CellItem(
    cell: Cell,
    index: Int,
    showVerticalDivider: Boolean,
    showHorizontalDivider: Boolean,
    onClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            modifier = Modifier.height(90.dp)
        ) {
            when (cell.type) {
                CellType.Nought -> CellImage(Res.drawable.ic_nought, modifier)
                CellType.Cross -> CellImage(Res.drawable.ic_cross, modifier)
                null -> Box(modifier = modifier.weight(1f)) {}
            }
            if (showVerticalDivider) {
                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    color = Grey
                )
            }
        }
        if (showHorizontalDivider) {
            Divider(
                color = Grey,
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun CellImage(
    resId: DrawableResource,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(resId),
        modifier = modifier,
        contentDescription = null
    )
}

@Composable
@Preview
private fun GameScreenPreview() {
    GameScreen()
}