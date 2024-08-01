package utils

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatcher {
    val IO: CoroutineDispatcher
}

expect fun provideDispatcher(): Dispatcher