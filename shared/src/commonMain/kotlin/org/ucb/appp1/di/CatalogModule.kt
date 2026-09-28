package org.ucb.appp1.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.ucb.appp1.catalog.data.datasource.remote.CatalogRemoteDataSource
import org.ucb.appp1.catalog.data.datasource.remote.CatalogService
import org.ucb.appp1.catalog.data.repository.CatalogRepositoryImpl
import org.ucb.appp1.catalog.domain.repository.CatalogRepository
import org.ucb.appp1.catalog.domain.usecase.GetMoviesUseCase
import org.ucb.appp1.catalog.presentation.viewmodel.CatalogViewModel

val catalogModule = module {
    single<CatalogRemoteDataSource> { CatalogService(get(), get()) }
    single<CatalogRepository> { CatalogRepositoryImpl(get()) }
    factory { GetMoviesUseCase(get()) }
    viewModelOf(::CatalogViewModel)
}
