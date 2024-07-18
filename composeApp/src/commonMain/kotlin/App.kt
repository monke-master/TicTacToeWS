import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.StartScreen
import ui.composable.GradientBackground
import ui.join.JoinGameScreen
import ui.theme.ApplicationTheme

@Composable
@Preview
fun App() {
    ApplicationTheme {
        JoinGameScreen()
    }
}
