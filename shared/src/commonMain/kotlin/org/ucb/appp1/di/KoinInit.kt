package org.ucb.appp1.di

import org.koin.core.context.startKoin

fun initKoin() {
    runCatching {
        startKoin {
            modules(sharedModules)
        }
    }
}
