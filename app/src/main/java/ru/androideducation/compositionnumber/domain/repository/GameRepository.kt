package ru.androideducation.compositionnumber.domain.repository

import ru.androideducation.compositionnumber.domain.entity.GameSettings
import ru.androideducation.compositionnumber.domain.entity.Level
import ru.androideducation.compositionnumber.domain.entity.Question

interface GameRepository {

    fun generateQuestions(
        maxSumValue: Int,
        countOfOption: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}