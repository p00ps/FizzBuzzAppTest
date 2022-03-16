package com.example.fizzbuzz.domain.interactor

import com.example.fizzbuzz.core.UseCaseWithParameter
import com.example.fizzbuzz.data.model.mapper.mapToData
import com.example.fizzbuzz.domain.repository.DataRepository
import com.example.fizzbuzz.presentation.model.UserInput
import javax.inject.Inject

class StoreUserInputUseCase @Inject constructor(
    private val dataRepository: DataRepository
) : UseCaseWithParameter<StoreUserInputUseCase.Param, Unit> {

    override suspend fun invoke(param: Param): Result<Unit> = dataRepository.storeUserInput(
        param.userInput.mapToData()
    )

    data class Param(
        val userInput: UserInput
    )
}

