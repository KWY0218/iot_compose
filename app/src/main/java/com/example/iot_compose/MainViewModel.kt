package com.example.iot_compose

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.iot_compose.repository.Repository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(private val repository: Repository): ViewModel(){
    val currentAngle:LiveData<Float> = repository.currentAngle
    val alarmState:LiveData<String> = repository.alarmState

    fun changeAngle(direction:String) = viewModelScope.launch {
            repository.changeAngle(direction)
        }
    fun changeAlarmState() = viewModelScope.launch {
        repository.changeAlarmState()
    }
}

class MainViewModelFactory(private val repository: Repository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}