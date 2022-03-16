package com.example.fizzbuzz.di

import com.example.fizzbuzz.data.datasource.FizzBuzzDataSource
import com.example.fizzbuzz.data.datasource.LocalDataSource
import com.example.fizzbuzz.data.repository.DataRepositoryImpl
import com.example.fizzbuzz.domain.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun bindDataRepository(
        impl: DataRepositoryImpl
    ): DataRepository = impl

    @Provides
    @Singleton
    fun provideLocalDataSource() : LocalDataSource = LocalDataSource()

    @Provides
    fun provideFizzBuzzDataSource() : FizzBuzzDataSource = FizzBuzzDataSource()
}