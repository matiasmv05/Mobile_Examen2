package org.ucb.appp1.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.ucb.appp1.catalog.data.datasource.remote.ApiConfig

val networkModule = module {
    single {
        val config: ApiConfig = get()
        HttpClient {
            expectSuccess = true
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 15_000
                requestTimeoutMillis = 30_000
                socketTimeoutMillis = 30_000
            }
            defaultRequest {
                url(config.baseUrl)
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = if (config.enableHttpLogging) LogLevel.INFO else LogLevel.NONE
            }
        }
    }
}

val sharedModules = listOf(networkModule)
