package ru.androideducation.compositionnumber.domain.entity

import java.io.Serializable

data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestion: Int,
    val GameSettings: GameSettings
) : Serializable