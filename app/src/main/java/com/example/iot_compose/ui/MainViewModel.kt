package com.example.iot_compose.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.iot_compose.state.Result
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iot_compose.usecase.ChangeAngleUseCase
import com.example.iot_compose.usecase.GetAngleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// 뷰와 repository 사이를 연결해주는 역할을 한다.
@HiltViewModel
class MainViewModel @Inject constructor(
    private val changeAngleUseCase: ChangeAngleUseCase,
    private val getAngleUseCase: GetAngleUseCase,
) : ViewModel() {
    var angleState by mutableStateOf(0F)
        private set

    /*
    * changeAngle : Repository 내에 있는 함수 changeAngle 을 실행하는 함수
    * changeAlarmState : Repository 내에 있는 함수 changeAlarmState 을 실행하는 함수
    */
    fun changeAngle(direction: String) = viewModelScope.launch {
        changeAngleUseCase(direction)
    }

    fun getAngle() = viewModelScope.launch {
        getAngleUseCase().collectLatest { angle->
            when(angle){
                is Result.Success<*> -> {
                    Log.d("MainViewModel","${angle.data}")
                    angleState = angle.data as Float
                }
                is Result.Fail<*> -> Log.d("MainViewModel","${angle.data}")
                else -> {Log.d("MainViewModel","loading")}
            }
        }
    }
}