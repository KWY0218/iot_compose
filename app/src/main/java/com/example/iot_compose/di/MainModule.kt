package com.example.iot_compose.di

import com.example.iot_compose.MainViewModel
import com.example.iot_compose.repository.Repository
import com.example.iot_compose.repository.RepositoryImpl
import com.example.iot_compose.usecase.ChangeAlarmStateUseCase
import com.example.iot_compose.usecase.ChangeAngleUseCase
import com.example.iot_compose.usecase.GetAlarmStateUseCase
import com.example.iot_compose.usecase.GetAngleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    fun provideRepository(): Repository{
        return RepositoryImpl()
    }

    @Provides
    fun provideViewModel(
        changeAngleUseCase: ChangeAngleUseCase,
        changeAlarmStateUseCase: ChangeAlarmStateUseCase,
        getAngleUseCase: GetAngleUseCase,
        getAlarmStateUseCase: GetAlarmStateUseCase
    ): MainViewModel{
        return MainViewModel(
            changeAngleUseCase,
            changeAlarmStateUseCase,
            getAngleUseCase,
            getAlarmStateUseCase
        )
    }
}