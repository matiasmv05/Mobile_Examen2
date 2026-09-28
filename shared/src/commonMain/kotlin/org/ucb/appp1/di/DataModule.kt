package org.ucb.appp1.di

import org.koin.dsl.module
import org.ucb.appp1.starwars.data.datasource.remote.CharacterRemoteDataSource
import org.ucb.appp1.starwars.data.datasource.remote.CharacterService
import org.ucb.appp1.starwars.data.repository.CharacterRepositoryImpl
import org.ucb.appp1.starwars.domain.repository.CharacterRepository

val dataModule = module {
    single<CharacterRemoteDataSource> { CharacterService(get()) }
    single<CharacterRepository> { CharacterRepositoryImpl(get()) }
}
