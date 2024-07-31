package di

import data.GameLocalDataSource
import data.GameLocalDataSourceImpl
import data.GameRemoteDataSource
import data.GameRemoteDataSourceImpl
import data.GameRepositoryImpl
import domain.CheckPlayerTurnUseCase
import domain.GameRepository
import domain.GetCurrentPlayerUseCase
import domain.GetGameSessionUseCase
import domain.HostGameUseCase
import domain.JoinGameUseCase
import domain.MakeTurnUseCase
import domain.StartGameUseCase
import io.ktor.serialization.WebsocketContentConverter
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import kotlinx.serialization.json.Json
import org.koin.dsl.module

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
        GameRepositoryImpl(get(), get())
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
}

val commonModules = listOf(
    domainModule,
    dataModule
)