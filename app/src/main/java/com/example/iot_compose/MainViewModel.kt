package com.example.iot_compose

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.iot_compose.repository.Repository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

// 뷰와 repository 사이를 연결해주는 역할을 한다.
class MainViewModel(private val repository: Repository) : ViewModel() {
    /*
    * currentAngle : Repository 내에 있는 변수 currentAngle 값을 저장하는 변수
    * alarmState : Repository 내에 있는 변수 alarmState 값을 저장하는 변수
    */
    val currentAngle: LiveData<Float> = repository.currentAngle
    val alarmState: LiveData<String> = repository.alarmState

    /*
    * changeAngle : Repository 내에 있는 함수 changeAngle 을 실행하는 함수
    * changeAlarmState : Repository 내에 있는 함수 changeAlarmState 을 실행하는 함수
    */
    fun changeAngle(direction: String) = viewModelScope.launch {
        repository.changeAngle(direction)
    }

    fun changeAlarmState() = viewModelScope.launch {
        repository.changeAlarmState()
    }
}

// 뷰 모델에 매개변수를 넣기 위한 클래스
class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}