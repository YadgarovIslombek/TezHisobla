package com.example.tezhisobla.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tezhisobla.data.GameRepositoryImpl
import com.example.tezhisobla.domain.entity.GameSetting
import com.example.tezhisobla.domain.entity.Level
import com.example.tezhisobla.domain.entity.Question
import com.example.tezhisobla.domain.usecases.GenerateQuestionUseCase
import com.example.tezhisobla.domain.usecases.GetGameSettingUseCase

class GameViewModel(application: Application) :AndroidViewModel(application) {
    val context = application
    val repository = GameRepositoryImpl

    val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    val getGameSettingUseCase = GetGameSettingUseCase(repository)

    private lateinit var level: Level
    private lateinit var gameSetting: GameSetting
    private var timer:CountDownTimer? = null

    private var countOfRightAnswer : Int = 0
    private var countOfQuestion : Int = 0

    private val _formatted =MutableLiveData<String>()
     val formatted : LiveData<String>
        get() = _formatted

    private val _question =MutableLiveData<Question>()
    val question : LiveData<Question>
        get() = _question

    fun startGame(level:Level){
        settingGame(level)
        startTime()
        generateQuestion()
    }

    private fun startTime(){
       timer = object : CountDownTimer(
            gameSetting.gameTimerSeconds* MILLI_IN_SECOND,
            MILLI_IN_SECOND){
            override fun onTick(millisUntilFinished: Long) {
                _formatted.value = formattedTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }
    private fun generateQuestion(){
        _question.value = generateQuestionUseCase(gameSetting.maxSumValue)
    }

    private fun chooseUserAnswer(number: Int){
        val realRightAnswer = question.value?.rightAnswer
        checkAnswer(realRightAnswer,number)
    }

    private fun checkAnswer(realRightAnswer:Int?,number:Int){
        if (realRightAnswer == number){
            countOfRightAnswer++
        }
        countOfQuestion++
        generateQuestion()
    }

    private fun finishGame() {
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    private fun formattedTime(millisUntilFinished: Long):String{
        val second = millisUntilFinished / MILLI_IN_SECOND
        val minute = second / SECOND_IN_MINUTE
        val leftTime = second-(minute* SECOND_IN_MINUTE)
        return String().format("%02d:%02d",minute,leftTime)
    }


    private fun settingGame(level: Level){
        this.level = level
        gameSetting = getGameSettingUseCase(level)
    }
    companion object{
        private const val MILLI_IN_SECOND = 1000L
        private const val SECOND_IN_MINUTE = 60
    }

}