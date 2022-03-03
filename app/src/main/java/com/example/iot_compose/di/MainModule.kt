package com.example.iot_compose.di

import com.example.iot_compose.ui.MainViewModel
import com.example.iot_compose.repository.Repository
import com.example.iot_compose.repository.RepositoryImpl
import com.example.iot_compose.usecase.ChangeAngleUseCase
import com.example.iot_compose.usecase.GetAngleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
        getAngleUseCase: GetAngleUseCase,
    ): MainViewModel {
        return MainViewModel(
            changeAngleUseCase,
            getAngleUseCase,
        )
    }
}