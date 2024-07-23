package ui.navigation

import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ui.StartScreen
import ui.game.GameScreen
import ui.host.HostGameScreen
import ui.join.JoinGameScreen

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
    data object GameNavRoute: NavRoute("game_screen")
}