package com.example.iot_compose.usecase

import com.example.iot_compose.repository.Repository
import javax.inject.Inject

class ChangeAlarmStateUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(){
        repository.changeAlarmState()
    }
}