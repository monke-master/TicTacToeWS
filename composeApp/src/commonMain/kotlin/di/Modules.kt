package di

import data.GameLocalDataSource
import data.GameLocalDataSourceImpl
import data.GameRemoteDataSource
import data.GameRemoteDataSourceImpl
import data.GameRepositoryImpl
import domain.usecase.CheckPlayerTurnUseCase
import domain.GameRepository
import domain.usecase.GetCurrentPlayerUseCase
import domain.usecase.GetGameSessionUseCase
import domain.usecase.HostGameUseCase
import domain.usecase.JoinGameUseCase
import domain.usecase.MakeTurnUseCase
import domain.usecase.QuitGameUseCase
import domain.usecase.RestartGameUseCase
import domain.usecase.StartGameUseCase
import io.ktor.serialization.WebsocketContentConverter
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import utils.provideDispatcher

val dataModule = module {
    single<WebsocketContentConverter> {
        KotlinxWebsocketSerializationConverter(Json)
    }
    single<GameRemoteDataSource> {
        GameRemoteDataSourceImpl(get())
    }
    single<GameLocalDataSource> {
        GameLocalDataSourceImpl()
    }
    single<GameRepository> {
        GameRepositoryImpl(
            gameLocalDataSource = get(),
            gameRemoteDataSource = get(),
            dispatcher = provideDispatcher()
        )
    }
}

val domainModule = module {
    single {
        HostGameUseCase(get())
    }
    single {
        StartGameUseCase(get())
    }
    single {
        JoinGameUseCase(get())
    }
    single {
        CheckPlayerTurnUseCase(get())
    }
    single {
        GetGameSessionUseCase(get())
    }
    single {
        MakeTurnUseCase(get())
    }
    single {
        GetCurrentPlayerUseCase(get())
    }
    single {
        QuitGameUseCase(get())
    }
    single {
        RestartGameUseCase(get())
    }
}

val commonModules = listOf(
    domainModule,
    dataModule
)