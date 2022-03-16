package com.example.fizzbuzz.presentation.viewmodel

import com.example.fizzbuzz.core.BaseMVIViewModel
import com.example.fizzbuzz.core.MVI
import com.example.fizzbuzz.domain.interactor.StoreUserInputUseCase
import com.example.fizzbuzz.presentation.model.UserInput
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val storeUserInputUseCase: StoreUserInputUseCase
) : BaseMVIViewModel<FormViewModel.EmptyState, FormViewModel.ViewAction>(
    EmptyState
) {

    object EmptyState : MVI.State

    sealed class ViewEvent : MVI.Event {
        object InputNotValid : ViewEvent()
        object GoToResultList : ViewEvent()
    }

    sealed class ViewAction : MVI.Intent {
        data class CheckInput(val userInput: UserInput) : ViewAction()
    }

    override suspend fun processIntent(intent: ViewAction) {
        when (intent) {
            is ViewAction.CheckInput -> checkUserInput(intent.userInput)
        }
    }

    private suspend fun checkUserInput(userInput: UserInput) {
        val isValid = userInput.numberInputOne > 0 &&
            userInput.numberInputTwo > 0 &&
            userInput.limitInput > 0 &&
            userInput.stringInputOne.isNotEmpty() &&
            userInput.stringInputTwo.isNotEmpty() &&
            (userInput.numberInputOne != userInput.numberInputTwo) &&
            (userInput.stringInputOne != userInput.stringInputTwo) &&
            userInput.numberInputOne < userInput.limitInput &&
            userInput.numberInputTwo < userInput.limitInput

            if (isValid) {
                storeUserInputUseCase(
                    StoreUserInputUseCase.Param(userInput = userInput)
                ).onSuccess {
                    emitEvent(ViewEvent.GoToResultList)
                }
            } else {
                emitEvent(ViewEvent.InputNotValid)
            }
    }
}
