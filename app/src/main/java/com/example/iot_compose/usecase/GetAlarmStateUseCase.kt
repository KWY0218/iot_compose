package com.example.iot_compose.usecase

import com.example.iot_compose.repository.Repository
import javax.inject.Inject
import com.example.iot_compose.state.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAlarmStateUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(): Flow<Result> = flow{
        while (true){
            val result = repository.getAlarmState()
            emit(result)
            delay(1000)
        }
    }
}