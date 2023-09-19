package com.example.tezhisobla.domain.usecases

import com.example.tezhisobla.domain.entity.GameSetting
import com.example.tezhisobla.domain.entity.Level
import com.example.tezhisobla.domain.repository.GameRepository

class GetGameSettingUseCase(private val repository: GameRepository) {
    operator fun invoke(level: Level):GameSetting{
        return repository.getGameSetting(level)
    }
}