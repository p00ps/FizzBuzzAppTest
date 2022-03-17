package com.example.fizzbuzz.data.repository

import com.example.fizzbuzz.data.datasource.FizzBuzzDataSource
import com.example.fizzbuzz.data.datasource.LocalDataSource
import com.example.fizzbuzz.data.model.UserInputData
import com.example.fizzbuzz.domain.repository.DataRepository
import com.example.fizzbuzz.smallRangeResult
import com.example.fizzbuzz.userBaseRangeResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DataRepositoryImplTest {

    private val fizzBuzzDataSource = mockk<FizzBuzzDataSource>()
    private val localDataSource = mockk<LocalDataSource>()

    private val smallLimitToCalculate = 10
    private val largeLimitToCalculate = 20

    private val userInputData = UserInputData(
        3, 5, "Fizz", "Buzz", 15
    )

    private lateinit var repository: DataRepository

    @Before
    fun setup() {
        repository = DataRepositoryImpl(
            localDataSource,
            fizzBuzzDataSource
        )

        coEvery {
            localDataSource.getCurrentPosition()
        } returns 0

        coEvery {
            localDataSource.getUserInputData()
        } returns userInputData
    }

    @Test
    fun `if user limit is smaller than calculation limit then return list sized of user limit`(): Unit =
        runTest {
            coEvery {
                fizzBuzzDataSource.getTransformLimit()
            } returns largeLimitToCalculate

            val intRange = (1..userInputData.limitInput)

            coEvery {
                fizzBuzzDataSource.transformRange(intRange, userInputData)
            } returns userBaseRangeResult

            val result = repository.getFirstResult()

            Assert.assertNotNull(result)
            Assert.assertEquals(result.getOrThrow().size, userInputData.limitInput)
        }

    @Test
    fun `if user limit is bigger than calculation limit then return list sized with max limit`(): Unit =
        runTest {
            coEvery {
                fizzBuzzDataSource.getTransformLimit()
            } returns smallLimitToCalculate

            val intRange = (1..smallLimitToCalculate)

            coEvery {
                fizzBuzzDataSource.transformRange(intRange, userInputData)
            } returns smallRangeResult

            coEvery {
                localDataSource.setCurrentPosition(any())
            } returns Unit

            val result = repository.getFirstResult()

            Assert.assertNotNull(result)
            Assert.assertEquals(result.getOrThrow().size, smallLimitToCalculate)

            coVerify(exactly = 1) {
                localDataSource.setCurrentPosition(smallLimitToCalculate)
            }
        }

}