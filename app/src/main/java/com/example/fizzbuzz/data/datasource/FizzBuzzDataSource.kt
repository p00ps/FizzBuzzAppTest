package com.example.fizzbuzz.data.datasource

import com.example.fizzbuzz.data.model.UserInputData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class FizzBuzzDataSource @Inject constructor() {

    fun getTransformLimit() = 1000

    fun transformRange(
        intRange: IntRange,
        userInputData: UserInputData
    ): List<String> {
        return intRange.map {
                when {
                    it % userInputData.numberInputOne == 0 && it % userInputData.numberInputTwo == 0 ->
                        "$it -> ${userInputData.stringInputOne}${userInputData.stringInputTwo}"
                    it % userInputData.numberInputTwo == 0 -> "$it -> ${userInputData.stringInputTwo}"
                    it % userInputData.numberInputOne == 0 -> "$it -> ${userInputData.stringInputOne}"
                    else -> "$it -> $it"
                }
            }.toList()
    }
}