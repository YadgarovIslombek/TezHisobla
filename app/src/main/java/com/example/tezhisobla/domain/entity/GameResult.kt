package com.example.tezhisobla.domain.entity

import java.io.Serializable

data class GameResult (
    val winner:Boolean,
    val countOfRightAnswer:Int,
    val countOfQuestion:Int,
    val gameSetting: GameSetting

):Serializable