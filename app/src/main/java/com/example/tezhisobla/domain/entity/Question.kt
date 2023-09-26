package com.example.tezhisobla.domain.entity

data class Question(
    val yigindi:Int,
    val visibleNumber:Int,
    val opitions:List<Int>
){
    val rightAnswer = yigindi - visibleNumber
}