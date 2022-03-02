package com.example.iot_compose.usecase

import com.example.iot_compose.repository.Repository
import javax.inject.Inject
import com.example.iot_compose.state.Result

class GetAlarmStateUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(): Result = repository.getAlarmState()
}