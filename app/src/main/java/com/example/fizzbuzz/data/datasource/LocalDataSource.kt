package com.example.fizzbuzz.data.datasource

import com.example.fizzbuzz.data.model.UserInputData
import javax.inject.Inject

class LocalDataSource @Inject constructor() {

    private lateinit var userInputData: UserInputData
    private var currentIntPosition: Int = 0

    fun updateUserInputData(data: UserInputData) {
        userInputData = data
        currentIntPosition = 0
    }

    fun getUserInputData() = userInputData

    fun getCurrentPosition() = currentIntPosition

    fun setCurrentPosition(addMore: Int) {
        currentIntPosition + addMore
    }

    fun resetPosition() {
        currentIntPosition = 0
    }
}