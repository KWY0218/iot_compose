package com.example.iot_compose.usecase

import com.example.iot_compose.repository.Repository
import javax.inject.Inject

class ChangeAngleUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(direction: String){
        repository.changeAngle(direction)
    }
}