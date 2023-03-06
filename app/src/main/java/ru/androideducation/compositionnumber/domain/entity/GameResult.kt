package ru.androideducation.compositionnumber.domain.entity

data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestion: Int,
    val GameSettings: GameSettings
)