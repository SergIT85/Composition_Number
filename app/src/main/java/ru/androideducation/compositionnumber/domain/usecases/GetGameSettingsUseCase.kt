package ru.androideducation.compositionnumber.domain.usecases

import ru.androideducation.compositionnumber.domain.entity.GameSettings
import ru.androideducation.compositionnumber.domain.entity.Level
import ru.androideducation.compositionnumber.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {
    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}