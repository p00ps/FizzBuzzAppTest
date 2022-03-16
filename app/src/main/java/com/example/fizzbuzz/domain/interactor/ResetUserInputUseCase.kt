package com.example.fizzbuzz.domain.interactor

import com.example.fizzbuzz.core.UseCase
import com.example.fizzbuzz.domain.repository.DataRepository
import javax.inject.Inject

class ResetUserInputUseCase @Inject constructor(
    private val dataRepository: DataRepository
) : UseCase<Unit> {

    override suspend fun invoke(): Result<Unit> = dataRepository.resetUserInput()
}

