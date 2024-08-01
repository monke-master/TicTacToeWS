package utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class IosDispatcher : Dispatcher {
    override val IO: CoroutineDispatcher
        get() = Dispatchers.Default
}

actual fun provideDispatcher(): Dispatcher = IosDispatcher()