package com.example.fizzbuzz.domain.repository

import com.example.fizzbuzz.data.model.UserInputData

interface DataRepository {

    suspend fun storeUserInput(userInputData: UserInputData): Result<Unit>
    suspend fun resetUserInput(): Result<Unit>

    suspend fun getFirstResult(): Result<List<String>>

    suspend fun getNextResult(): Result<List<String>>
}