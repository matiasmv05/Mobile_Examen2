package org.ucb.appp1.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.ucb.appp1.login.data.repository.LoginRepositoryImpl
import org.ucb.appp1.login.domain.repository.LoginRepository
import org.ucb.appp1.login.domain.usecase.LoginUseCase
import org.ucb.appp1.login.presentation.viewmodel.LoginViewModel

val loginModule = module {
    single<LoginRepository> { LoginRepositoryImpl() }
    factory { LoginUseCase(get()) }
    viewModelOf(::LoginViewModel)
}
