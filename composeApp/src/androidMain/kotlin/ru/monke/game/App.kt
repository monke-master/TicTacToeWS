package ru.monke.game

import android.app.Application
import di.commonModules
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(commonModules)
        }
    }
}