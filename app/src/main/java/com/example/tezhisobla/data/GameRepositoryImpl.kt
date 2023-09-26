package com.example.tezhisobla.data

import com.example.tezhisobla.domain.entity.GameSetting
import com.example.tezhisobla.domain.entity.Level
import com.example.tezhisobla.domain.entity.Question
import com.example.tezhisobla.domain.repository.GameRepository
import java.lang.Integer.min
import java.lang.Math.max
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {
    private const val MIN_SUM_VALUE = 2
    private const val MIN_SUM_VALUE_ANSWER = 1
    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val yigindi = Random.nextInt(MIN_SUM_VALUE_ANSWER, maxSumValue+1) //27
        val visibleNumber = Random.nextInt(MIN_SUM_VALUE, maxSumValue) //15
        val rightAnswer = yigindi - visibleNumber
        val variantlar = HashSet<Int>()
        variantlar.add(rightAnswer)
        val from = max(rightAnswer - maxSumValue, MIN_SUM_VALUE_ANSWER)//1
        val to = min(maxSumValue - 1, rightAnswer + countOfOptions)
        while (variantlar.size < countOfOptions) {
            variantlar.add(Random.nextInt(from, to))
        }
        return Question(yigindi, visibleNumber, variantlar.toList())
    }

    override fun getGameSetting(level: Level): GameSetting = when (level) {
        Level.TEST -> GameSetting(10, 5, 50, 8)
        Level.EASY -> GameSetting(10, 10, 70, 60)
        Level.NORMAL -> GameSetting(20, 20, 80, 40)
        Level.HARD -> GameSetting(30, 30, 90, 45)
    }
}