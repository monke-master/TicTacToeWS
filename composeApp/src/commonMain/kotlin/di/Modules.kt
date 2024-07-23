package di

import data.GameRemoteDataSource
import data.GameRemoteDataSourceImpl
import data.GameRepositoryImpl
import domain.GameRepository
import domain.HostGameUseCase
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
    single<GameRepository> {
        GameRepositoryImpl(get())
    }
}

val domainModule = module {
    single {
        HostGameUseCase(get())
    }
    single {
        StartGameUseCase(get())
    }
}

val commonModules = listOf(
    domainModule,
    dataModule
)