package ui.navigation

import host.HostGameScreen
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ui.StartScreen
import ui.game.GameScreen
import ui.join.JoinGameScreen

fun RootComposeBuilder.navGraph() {
    screen(Screen.StartScreen.route) {
        StartScreen()
    }
    screen(Screen.HostGameScreen.route) {
        HostGameScreen()
    }
    screen(Screen.JoinGameScreen.route) {
        JoinGameScreen()
    }
    screen(Screen.HostGameScreen.route) {
        GameScreen()
    }
}

sealed class Screen(
    val route: String
) {
    data object StartScreen: Screen("start_screen")
    data object HostGameScreen: Screen("host_screen")
    data object JoinGameScreen: Screen("join_screen")
    data object GameScreen: Screen("game_screen")
}