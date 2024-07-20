package ru.monke.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.StartScreen
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import ui.navigation.NavRoute
import ui.navigation.navGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val odysseyConfiguration = OdysseyConfiguration(
                canvas = this,
                startScreen = StartScreen.Custom(NavRoute.StartNavRoute.route)
            )

            setNavigationContent(configuration = odysseyConfiguration) {
                navGraph()
            }
        }
    }
}
