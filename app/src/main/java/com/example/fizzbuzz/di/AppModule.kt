package com.example.fizzbuzz.di

import com.example.fizzbuzz.data.datasource.FizzBuzzDataSource
import com.example.fizzbuzz.data.datasource.LocalDataSource
import com.example.fizzbuzz.data.repository.DataRepositoryImpl
import com.example.fizzbuzz.domain.repository.DataRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun bindDataRepository(
        dataRepositoryImpl: DataRepositoryImpl
    ): DataRepository = dataRepositoryImpl

    @Provides
    @Singleton
    fun provideLocalDataSource() : LocalDataSource = LocalDataSource()

    @Provides
    @Singleton
    fun provideFizzBuzzDataSource() : FizzBuzzDataSource = FizzBuzzDataSource()
}