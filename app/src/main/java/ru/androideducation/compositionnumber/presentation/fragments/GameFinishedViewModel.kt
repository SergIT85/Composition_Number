package ru.androideducation.compositionnumber.presentation.fragments

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.androideducation.compositionnumber.R
import ru.androideducation.compositionnumber.domain.entity.GameResult
import java.lang.RuntimeException

class GameFinishedViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application
    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _needCountRightAnswers = MutableLiveData<String>()
    val needCountRightAnswers: LiveData<String>
        get() = _needCountRightAnswers

    private val _yourScore = MutableLiveData<String>()
    val yourScore: LiveData<String>
        get() = _yourScore

    private val _needPercentRightAnswers = MutableLiveData<String>()
    val needPercentRightAnswers: LiveData<String>
        get() = _needPercentRightAnswers

    private val _percentRightAnswers = MutableLiveData<String>()
    val percentRightAnswers: LiveData<String>
        get() = _percentRightAnswers


    fun getAllGameResult() {
        getNeedCountRightAnswers()
        getYourScore()
        getNeedPercentRightAnswers()
        getPercentRightAnswers()
    }

    private fun getNeedCountRightAnswers() {
        _needCountRightAnswers.value = String.format(
            context.resources.getString(R.string.required_score),
            gameResult.value?.GameSettings?.minCountOfRightAnswers.toString()
        )
    }

    private fun getYourScore() {
        _yourScore.value = String.format(
            context.resources.getString(R.string.score_answers),
            gameResult.value?.countOfRightAnswers.toString()
        )
    }

    private fun getNeedPercentRightAnswers() {
        _needPercentRightAnswers.value = String.format(
            context.resources.getString(R.string.required_percentage),
            gameResult.value?.GameSettings?.minPercentOfRightAnswers
        )
    }

    private fun getPercentRightAnswers() {
        _percentRightAnswers.value = String.format(
            context.resources.getString(R.string.score_percentage),
            calculatePercentOfRightAnswers()
            )
    }

    private fun calculatePercentOfRightAnswers(): Int {
        return if(gameResult.value?.countOfRightAnswers != 0) {
            ((gameResult.value?.countOfRightAnswers?.div(
                gameResult.value?.countOfQuestion?.toDouble()!!
            ))?.times(100))?.toInt()
                ?: throw RuntimeException("Trouble in fun calculatePercentOfRightAnswers" +
                        " in GameFinishedViewModel")
        } else {
            0
        }
    }

    fun getGameResult(gameResult: GameResult) {
        _gameResult.value = gameResult
    }
}