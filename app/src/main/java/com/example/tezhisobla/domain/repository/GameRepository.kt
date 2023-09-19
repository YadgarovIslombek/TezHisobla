package com.example.tezhisobla.domain.repository

import com.example.tezhisobla.domain.entity.GameSetting
import com.example.tezhisobla.domain.entity.Level
import com.example.tezhisobla.domain.entity.Question

interface GameRepository {
    fun generateQuestion(maxSumValue:Int,countOfOptions:Int):Question
    fun getGameSetting(level:Level):GameSetting
}