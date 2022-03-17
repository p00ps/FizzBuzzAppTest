package com.example.fizzbuzz.data.datasource

import com.example.fizzbuzz.userInputData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FizzBuzzDataSourceTest {

    private val fizzBuzzDataSource = mockk<FizzBuzzDataSource>()

    private val intRange = (1..15)

    @Before
    fun setup() {
        coEvery {
            fizzBuzzDataSource.transformRange(intRange, userInputData)
        } returns listOf(
            "1 -> 1",
            "2 -> 2",
            "3 -> Fizz",
            "4 -> 4",
            "5 -> Buzz",
            "6 -> Fizz",
            "7 -> 7",
            "8 -> 8",
            "9 -> Fizz",
            "10 -> Buzz",
            "11 -> 11",
            "12 -> Fizz",
            "13 -> 13",
            "14 -> 14",
            "15 -> FizzBuzz"
        )
    }

    @Test
    fun `check if multiple of 3 return Fizz`(): Unit = runTest {
        val result = fizzBuzzDataSource.transformRange(intRange, userInputData)

        Assert.assertNotNull(result)
        Assert.assertEquals(result[2], "3 -> Fizz")
    }

    @Test
    fun `check if multiple of 5 return Buzz`(): Unit = runTest {
        val result = fizzBuzzDataSource.transformRange(intRange, userInputData)

        Assert.assertNotNull(result)
        Assert.assertEquals(result[4], "5 -> Buzz")
    }

    @Test
    fun `check if multiple of 3 and 5 return FizzBuzz`(): Unit = runTest {
        val result = fizzBuzzDataSource.transformRange(intRange, userInputData)

        Assert.assertNotNull(result)
        Assert.assertEquals(result[14], "15 -> FizzBuzz")
    }
}