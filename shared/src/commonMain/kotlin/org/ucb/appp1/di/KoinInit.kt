package org.ucb.appp1.di

import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.ucb.appp1.catalog.data.datasource.remote.ApiConfig

private val featureModules = listOf(
    catalogModule,
    loginModule,
    registerModule
)

fun initKoin(apiConfig: ApiConfig = ApiConfig()) {
    runCatching {
        startKoin {
            modules(sharedModules + featureModules + module { single { apiConfig } })
        }
    }
}
