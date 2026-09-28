package org.ucb.appp1.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.ucb.appp1.starwars.presentation.viewmodel.CharacterViewModel

val presentationModule = module {
    viewModelOf(::CharacterViewModel)
}
