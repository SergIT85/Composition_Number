package ru.androideducation.compositionnumber.domain.usecases

import ru.androideducation.compositionnumber.domain.entity.Question
import ru.androideducation.compositionnumber.domain.repository.GameRepository

class GenerateQuestionsUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestions(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}