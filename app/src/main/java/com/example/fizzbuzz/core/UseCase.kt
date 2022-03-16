package com.example.fizzbuzz.core

interface UseCaseWithParameter<P, R> {
    suspend operator fun invoke(param: P): Result<R>
}

interface UseCase<R> {
    suspend operator fun invoke(): Result<R>
}