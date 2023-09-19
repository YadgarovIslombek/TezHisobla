package com.example.tezhisobla.domain.entity

data class GameResult (
    val winner:Boolean,
    val countOfRightAnswer:Int,
    val countOfQuestion:Int,
    val gameSetting: GameSetting

)