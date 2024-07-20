import androidx.compose.ui.window.ComposeUIViewController
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.StartScreen
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import ui.navigation.NavRoute
import ui.navigation.navGraph

fun MainViewController() = ComposeUIViewController {
    val odysseyConfiguration = OdysseyConfiguration(
        startScreen = StartScreen.Custom(NavRoute.StartNavRoute.route)
    )

    setNavigationContent(odysseyConfiguration) {
        navGraph()
    }
}