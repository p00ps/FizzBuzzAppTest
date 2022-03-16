package com.example.fizzbuzz.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.fizzbuzz.core.BaseMVIViewModel
import com.example.fizzbuzz.core.MVI
import com.example.fizzbuzz.domain.interactor.GetFirstResultUseCase
import com.example.fizzbuzz.domain.interactor.GetNextResultUseCase
import com.example.fizzbuzz.domain.interactor.ResetUserInputUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val resetUserInputUseCase: ResetUserInputUseCase,
    private val getFirstResultUseCase: GetFirstResultUseCase,
    private val getNextResultUseCase: GetNextResultUseCase
) : BaseMVIViewModel<ResultViewModel.EmptyState, ResultViewModel.ViewAction>(EmptyState){

    object EmptyState : MVI.State

    sealed class ViewEvent : MVI.Event {
        object Error : ViewEvent()
        data class FizzBizzRangeResult(val result: List<String>) : ViewEvent()
    }

    sealed class ViewAction : MVI.Intent {
        object GetFirstRange : ViewAction()
        object GetNextRange : ViewAction()
        object Reset : ViewAction()
    }

    override suspend fun processIntent(intent: ViewAction) {
        when (intent) {
            ViewAction.GetFirstRange -> getFirstResult()
            ViewAction.GetNextRange -> getNextResult()
            ViewAction.Reset -> reset()
        }
    }

    private suspend fun getFirstResult() {
            getFirstResultUseCase().onSuccess {
                emitEvent(ViewEvent.FizzBizzRangeResult(it))
            }.onFailure {
                emitEvent(ViewEvent.Error)
            }
    }

    private suspend fun getNextResult() {
        getNextResultUseCase().onSuccess {
            emitEvent(ViewEvent.FizzBizzRangeResult(it))
        }.onFailure {
            emitEvent(ViewEvent.Error)
        }
    }

    private suspend fun reset() {
        viewModelScope.coroutineContext.cancelChildren()
        resetUserInputUseCase()
    }
}
