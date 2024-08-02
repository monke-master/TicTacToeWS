package ui.navigation

import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ui.StartScreen
import ui.game.GameScreen
import ui.host.HostGameScreen
import ui.join.JoinGameScreen
import ui.join.qr_scanner.QrCodeScannerScreen

fun RootComposeBuilder.navGraph() {
    screen(NavRoute.StartNavRoute.route) {
        StartScreen()
    }
    screen(NavRoute.HostGameNavRoute.route) {
        HostGameScreen()
    }
    screen(NavRoute.JoinGameNavRoute.route) {
        JoinGameScreen()
    }
    screen(NavRoute.ScanQrNavRoute.route) { params ->
        QrCodeScannerScreen(params as ((String) -> Unit))
    }
    screen(NavRoute.GameNavRoute.route) {
        GameScreen()
    }
}


sealed class NavRoute(
    val route: String
) {
    data object StartNavRoute: NavRoute("start_screen")
    data object HostGameNavRoute: NavRoute("host_screen")
    data object JoinGameNavRoute: NavRoute("join_screen")
    data object ScanQrNavRoute: NavRoute("qr_scanner")
    data object GameNavRoute: NavRoute("game_screen")
}