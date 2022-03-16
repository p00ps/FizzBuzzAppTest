package com.example.fizzbuzz.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Fragment
 */
public inline fun <S : MVI.State, I : MVI.Intent> Fragment.processState(
    viewModel: BaseMVIViewModel<S, I>,
    crossinline action: S.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.uiState.map { action(it) }
                .launchIn(this)
        }
    }
}

public inline fun <reified E : MVI.Event> Fragment.processEvent(
    viewModel: BaseMVIViewModel<*, *>,
    crossinline action: E.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.uiEvent.map { action(it as E) }.launchIn(this)
        }
    }
}