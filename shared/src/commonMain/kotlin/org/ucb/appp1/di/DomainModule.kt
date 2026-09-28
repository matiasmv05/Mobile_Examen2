package org.ucb.appp1.di

import org.koin.dsl.module
import org.ucb.appp1.starwars.domain.usecase.GetCharactersUseCase

val domainModule = module {
    factory { GetCharactersUseCase(get()) }
}
