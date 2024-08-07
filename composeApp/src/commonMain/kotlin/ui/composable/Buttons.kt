package ui.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,

        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h4,
            color = textColor
        )
    }
}

@Composable
@Preview
fun TextButtonPreview() {
    MaterialTheme {
        TextButton(
            text = "Goida"
        ) {}
    }
}