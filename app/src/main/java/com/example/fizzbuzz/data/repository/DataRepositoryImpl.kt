package com.example.fizzbuzz.data.repository

import com.example.fizzbuzz.data.datasource.FizzBuzzDataSource
import com.example.fizzbuzz.data.datasource.LocalDataSource
import com.example.fizzbuzz.data.model.UserInputData
import com.example.fizzbuzz.domain.repository.DataRepository
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val fizzBuzzDataSource: FizzBuzzDataSource
) : DataRepository {

    override suspend fun storeUserInput(userInputData: UserInputData): Result<Unit> =
        localDataSource.updateUserInputData(userInputData).let {
            Result.success(Unit)
        }

    override suspend fun resetUserInput(): Result<Unit> {
        return localDataSource.resetPosition().let {
            Result.success(Unit)
        }
    }

    override suspend fun getFirstResult(): Result<List<String>> {
        val userLimit = localDataSource.getUserInputData().limitInput
        val maxToCalculate = fizzBuzzDataSource.getTransformLimit()
        return try {
            if (userLimit <= maxToCalculate) {
                Result.success(
                    fizzBuzzDataSource.transformRange(
                        1..userLimit,
                        localDataSource.getUserInputData()
                    )
                )
            } else {
                Result.success(
                    fizzBuzzDataSource.transformRange(
                        1..maxToCalculate,
                        localDataSource.getUserInputData()
                    )
                ).also {
                    localDataSource.setCurrentPosition(it.getOrThrow().size)
                }
            }
        } catch (e: Exception) {
            Result.failure(Throwable(e.message, e))
        }
    }

    override suspend fun getNextResult(): Result<List<String>> {
        val userLimit = localDataSource.getUserInputData().limitInput
        val currentPosition = localDataSource.getCurrentPosition()
        val maxToCalculate = fizzBuzzDataSource.getTransformLimit()
        return try {
            if (userLimit - currentPosition <= maxToCalculate) {
                Result.success(
                    fizzBuzzDataSource.transformRange(
                        currentPosition..userLimit,
                        localDataSource.getUserInputData()
                    )
                )
            } else {
                Result.success(
                    fizzBuzzDataSource.transformRange(
                        currentPosition..maxToCalculate,
                        localDataSource.getUserInputData()
                    )
                ).also {
                    localDataSource.setCurrentPosition(it.getOrThrow().size)
                }
            }
        } catch (e: Exception) {
            Result.failure(Throwable(e.message, e))
        }
    }
}