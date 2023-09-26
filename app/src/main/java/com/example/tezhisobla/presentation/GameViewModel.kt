package com.example.tezhisobla.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tezhisobla.R
import com.example.tezhisobla.data.GameRepositoryImpl
import com.example.tezhisobla.domain.entity.GameResult
import com.example.tezhisobla.domain.entity.GameSetting
import com.example.tezhisobla.domain.entity.Level
import com.example.tezhisobla.domain.entity.Question
import com.example.tezhisobla.domain.usecases.GenerateQuestionUseCase
import com.example.tezhisobla.domain.usecases.GetGameSettingUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {
    val context = application
    val repository = GameRepositoryImpl

    val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    val getGameSettingUseCase = GetGameSettingUseCase(repository)

    private lateinit var level: Level
    private lateinit var gameSetting: GameSetting
    private lateinit var timer: CountDownTimer

    private var countOfRightAnswer: Int = 0
    private var countOfQuestion: Int = 0

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _percentRightOfAnswer = MutableLiveData<Int>()
    val percentRightOfAnswer: LiveData<Int>
        get() = _percentRightOfAnswer

    private val _progressAnswer = MutableLiveData<String>()
    val progressAnswer: LiveData<String>
        get() = _progressAnswer


    private val _enoughCountRightOfAnswer = MutableLiveData<Boolean>()
    val enoughCountRightOfAnswer: LiveData<Boolean>
        get() = _enoughCountRightOfAnswer


    private val _enoughPercentOfAnswer = MutableLiveData<Boolean>()
    val enoughPercentOfAnswer: LiveData<Boolean>
        get() = _enoughPercentOfAnswer

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent


    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    fun startGame(level: Level) {
        settingGame(level)
        _minPercent.value = gameSetting.minPercentOfRightAnswer
        startTime()
        generateQuestion()
        uptadeProgress()

    }

    fun chooseAnswer(number: Int) {
        val realRightAnswer = question.value?.rightAnswer
        checkAnswer(realRightAnswer, number)

    }

    private fun checkAnswer(realRightAnswer: Int?, number: Int) {
        if (realRightAnswer == number) {
            countOfRightAnswer++
        }
        countOfQuestion++
        uptadeProgress()
        generateQuestion()
    }

    private fun uptadeProgress() {
        val percent = calculatePersentOfRightAnswer()
        _percentRightOfAnswer.value = percent
        _progressAnswer.value = String.format(
            context.getString(R.string.progress_answer),
            countOfRightAnswer,
            gameSetting.minCountOfRightAnswer
        )
        _enoughCountRightOfAnswer.value = countOfRightAnswer >= gameSetting.minCountOfRightAnswer
        _enoughPercentOfAnswer.value = percent >= gameSetting.minPercentOfRightAnswer
    }

    private fun calculatePersentOfRightAnswer(): Int {
        if (countOfQuestion == 0) {
            return 0
        }
        return ((countOfRightAnswer / countOfQuestion.toDouble()) * 100).toInt()
    }

    private fun startTime() {
        timer = object : CountDownTimer(
            gameSetting.gameTimerSeconds * MILLI_IN_SECOND, MILLI_IN_SECOND
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formattedTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }


        }
        timer.start()
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSetting.maxSumValue)
    }


    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughPercentOfAnswer.value == true && enoughCountRightOfAnswer.value == true,
            countOfRightAnswer,
            countOfQuestion,
            gameSetting
        )
    }


    private fun formattedTime(millisUntilFinished: Long): String {
        val second = millisUntilFinished / MILLI_IN_SECOND
        val minute = second / SECOND_IN_MINUTE
        val leftTime = second - (minute * SECOND_IN_MINUTE)
//        return String().format("%02d:%02d", minute, leftTime)
        return String.format(
            "%02d:%02d",
            minute,
            leftTime
        )///shu yerda qavs qolib getgan akan 40 min vaxtim getti((((
    }


    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }


    private fun settingGame(level: Level) {
        this.level = level
        gameSetting = getGameSettingUseCase(level)
    }

    companion object {
        private const val MILLI_IN_SECOND = 1000L
        private const val SECOND_IN_MINUTE = 60
    }

}