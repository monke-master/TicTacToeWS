import di.commonModules
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(commonModules)
    }
}