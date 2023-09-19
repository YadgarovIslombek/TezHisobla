package com.example.tezhisobla.domain.usecases

import com.example.tezhisobla.domain.entity.Question
import com.example.tezhisobla.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {

    operator fun invoke(maxSumValue:Int):Question{
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTION)
    }
    companion object{
        const val COUNT_OF_OPTION = 6
    }
}