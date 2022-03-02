package com.example.iot_compose.state

sealed class Result {
    object Loading: Result()
    data class Success<T>(val data: T) : Result()
    data class Fail<T>(val data: T) : Result()
}