package di

import data.GameRemoteDataSource
import data.GameRemoteDataSourceImpl
import data.GameRepositoryImpl
import domain.GameRepository
import domain.HostGameUseCase
import org.koin.dsl.module

val dataModule = module {
    single<GameRemoteDataSource> {
        GameRemoteDataSourceImpl()
    }
    single<GameRepository> {
        GameRepositoryImpl(get())
    }
}

val domainModule = module {
    single {
        HostGameUseCase(get())
    }
}

val commonModules = listOf(
    domainModule,
    dataModule
)