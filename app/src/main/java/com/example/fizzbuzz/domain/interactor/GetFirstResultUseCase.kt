package com.example.fizzbuzz.domain.interactor

import com.example.fizzbuzz.core.UseCase
import com.example.fizzbuzz.domain.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFirstResultUseCase @Inject constructor(
    private val dataRepository: DataRepository
) : UseCase<List<String>> {

    override suspend fun invoke(): Result<List<String>> = withContext(Dispatchers.IO) {
        dataRepository.getFirstResult()
    }
}