package com.example.fizzbuzz.data.model.mapper

import com.example.fizzbuzz.data.model.UserInputData
import com.example.fizzbuzz.presentation.model.UserInput

fun UserInput.mapToData(): UserInputData = UserInputData(
    numberInputOne, numberInputTwo, stringInputOne, stringInputTwo, limitInput
)