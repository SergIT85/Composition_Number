package ru.androideducation.compositionnumber.presentation.fragments

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.androideducation.compositionnumber.R
import ru.androideducation.compositionnumber.data.GameRepositoryImpl
import ru.androideducation.compositionnumber.domain.entity.GameResult
import ru.androideducation.compositionnumber.domain.entity.GameSettings
import ru.androideducation.compositionnumber.domain.entity.Level
import ru.androideducation.compositionnumber.domain.entity.Question
import ru.androideducation.compositionnumber.domain.usecases.GenerateQuestionsUseCase
import ru.androideducation.compositionnumber.domain.usecases.GetGameSettingsUseCase
import java.lang.RuntimeException

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level

    private var timer: CountDownTimer? = null

    private val context = application
    private val repository = GameRepositoryImpl

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    private val generateQuestionsUseCase = GenerateQuestionsUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)


    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    fun startGame(level: Level) {
        getGameSettings(level)
        startTimer()
        getQuestion()
    }

    fun chooseAnswer(answer: Int) {
        checkAnswer(answer)
        updateProgress()
        getQuestion()
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCount.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercent.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(answer: Int) {
        val rightAnswer = question.value?.rightAnswer()
        if(answer == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++

    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formattedTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }

        }
    }

    private fun formattedTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun finishGame(){

    }


    private fun getLevel(level: Level): Level {
        return level
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }


    private fun rightAnswer(question: LiveData<Question>): Int {
        return question.value?.visibleNumber?.let {
            question.value?.sum?.minus(it)
        } ?: throw RuntimeException("Problem in rightAnswer formula ")
    }

    private fun getQuestion() {
        _question.value = generateQuestionsUseCase.invoke(gameSettings.maxSumValue)

    }

    companion object {

        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }


}