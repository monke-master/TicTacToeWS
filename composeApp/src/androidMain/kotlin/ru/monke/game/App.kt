package ru.monke.game

import android.app.Application
import di.commonModules
import org.koin.core.context.startKoin
import utils.AndroidDeviceId

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        AndroidDeviceId.contentResolver = contentResolver

        startKoin {
            modules(commonModules)
        }
    }
}