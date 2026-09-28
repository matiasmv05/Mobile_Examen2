package org.ucb.appp1.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.ucb.appp1.register.data.repository.RegisterRepositoryImpl
import org.ucb.appp1.register.domain.repository.RegisterRepository
import org.ucb.appp1.register.domain.usecase.RegisterUseCase
import org.ucb.appp1.register.presentation.viewmodel.RegisterViewModel

val registerModule = module {
    single<RegisterRepository> { RegisterRepositoryImpl() }
    factory { RegisterUseCase(get()) }
    viewModelOf(::RegisterViewModel)
}
