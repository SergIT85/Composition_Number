package ru.androideducation.compositionnumber.domain.entity

data class Question(
    val sum: Int,
    val visibleNumber: Int,
    val options: List<Int>
) {
    fun rightAnswer(): Int {
        return sum - visibleNumber
    }
}