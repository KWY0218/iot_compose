package com.example.iot_compose.repository

import com.example.iot_compose.state.Result

interface Repository {
    suspend fun getAngle(): Result
    suspend fun getAlarmState(): Result
    fun changeAngle(direction:String)
    fun changeAlarmState()
}