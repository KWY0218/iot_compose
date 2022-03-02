package com.example.iot_compose

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.iot_compose.state.Result
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iot_compose.usecase.ChangeAlarmStateUseCase
import com.example.iot_compose.usecase.ChangeAngleUseCase
import com.example.iot_compose.usecase.GetAlarmStateUseCase
import com.example.iot_compose.usecase.GetAngleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

// 뷰와 repository 사이를 연결해주는 역할을 한다.
@HiltViewModel
class MainViewModel @Inject constructor(
    private val changeAngleUseCase: ChangeAngleUseCase,
    private val changeAlarmStateUseCase: ChangeAlarmStateUseCase,
    private val getAngleUseCase: GetAngleUseCase,
    private val getAlarmStateUseCase: GetAlarmStateUseCase
) : ViewModel() {
    var uiState by mutableStateOf(UiState())
        private set
    init {
        getAlarmState()
        getAngle()
    }
    /*
    * changeAngle : Repository 내에 있는 함수 changeAngle 을 실행하는 함수
    * changeAlarmState : Repository 내에 있는 함수 changeAlarmState 을 실행하는 함수
    */
    fun changeAngle(direction: String) = viewModelScope.launch {
        changeAngleUseCase(direction)
    }

    fun changeAlarmState() = viewModelScope.launch {
        changeAlarmStateUseCase()
    }

    fun getAngle() = viewModelScope.launch {
        when(val result = getAngleUseCase()){
            is Result.Success<*> -> {
                Log.d("MainViewModel","${result.data}")
                uiState = uiState.copy(angle = result.data as Float)
            }
            is Result.Fail<*> -> Log.d("MainViewModel","${result.data}")
            else -> {Log.d("MainViewModel","loading")}
        }
    }

    fun getAlarmState() = viewModelScope.launch {
        when(val result = getAlarmStateUseCase()){
            is Result.Success<*> -> {
                Log.d("MainViewModel","${result.data}")
                uiState = uiState.copy(alarmState = result.data as String)
            }
            is Result.Fail<*> -> Log.d("MainViewModel","${result.data}")
            else -> {Log.d("MainViewModel","loading")}
        }
    }
}

data class UiState(
    val angle: Float = 0F,
    val alarmState: String = ""
)