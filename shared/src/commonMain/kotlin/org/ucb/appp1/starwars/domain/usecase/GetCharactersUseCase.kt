package org.ucb.appp1.starwars.domain.usecase

import org.ucb.appp1.starwars.domain.model.CharacterPageModel
import org.ucb.appp1.starwars.domain.repository.CharacterRepository

class GetCharactersUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(page: Int = 1): Result<CharacterPageModel> =
        repository.getCharacters(page)
}
