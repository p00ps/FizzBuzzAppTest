package com.example.fizzbuzz.core

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly

abstract class BaseMVIViewModel<S : MVI.State, I : MVI.Intent>(
    private val firstModel: S,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
) : androidx.lifecycle.ViewModel(),
    MVI.ViewModel<S, I> {

    val mviScope: CoroutineScope = viewModelScope + dispatcher

    private var _currentState: S = firstModel
    protected val currentState: S get() = _currentState

    private val _uiState = MutableSharedFlow<S>(
        replay = 1,
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    /**
     * Flow that keeps track of the UI State
     */
    val uiState: SharedFlow<S> =
        _uiState.onStart {
            emit(firstModel)
        }.onEach {
            log("state consumed: $it")
        }.shareIn(
            scope = mviScope,
            started = Eagerly,
            replay = 1
        )

    private val _uiEvent: MutableSharedFlow<MVI.Event> = MutableSharedFlow(
        extraBufferCapacity = 1,
    )

    /**
     * Flow that pop one-shot events
     *
     * It does not keep tracks of any messages, if there is no subscriber no one will here the message.
     */
    public val uiEvent: SharedFlow<MVI.Event> = _uiEvent.asSharedFlow()

    /**
     * Publishes a new state
     */
    protected fun publishState(state: S) {
        val published = _uiState.tryEmit(state)
        if (published) {
            _currentState = state
        }
    }

    /**
     * Emits a one-shot event
     */
    protected fun emitEvent(event: MVI.Event) {
        mviScope.launch {
            _uiEvent.emit(event)
            log("event emitted: $event")
        }
    }

    fun <P : MVI.State> reduceAndPublishState(reducer: MVI.Reducer<S, P>, partial: P) {
        val newState = reducer.reduce(currentState, partial)
        val published = _uiState.tryEmit(newState)
        if (published) {
            _currentState = newState
        }
    }

    protected abstract suspend fun processIntent(intent: I)

    final override fun sendIntent(intent: I) {
        mviScope.launch {
            log("process intent: $intent")
            processIntent(intent)
        }
    }

    private fun log(message: String) = Log.d(this::class.java.simpleName, message)
}

object MVI {

    interface State

    interface Intent

    interface Event

    interface ViewModel<S : State, I : Intent> {

        fun sendIntent(intent: I)
    }

    fun interface Reducer<S : State, P : State> {
        fun reduce(previousState: S, partialState: P): S
    }
}
