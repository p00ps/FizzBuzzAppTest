package com.example.fizzbuzz.core

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch

public abstract class BaseMVIViewModel<S : MVI.State, I : MVI.Intent>(
    private val firstModel: S,
) : androidx.lifecycle.ViewModel(),
    MVI.ViewModel<S, I> {

    protected val currentState: S get() = _uiState.replayCache.firstOrNull() ?: firstModel

    private val _uiState = MutableSharedFlow<S>(replay = 1, extraBufferCapacity = 64)

    /**
     * Flow that keeps track of the UI State
     */
    public val uiState: SharedFlow<S> =
        _uiState.onStart { emit(firstModel) }
            .onEach { log("new state: $it") }
            .shareIn(
                scope = viewModelScope,
                started = WhileSubscribed(5000),
                replay = 1
            )

    private val _uiEvent: MutableSharedFlow<MVI.Event> = MutableSharedFlow<MVI.Event>(
        extraBufferCapacity = 1,
    ).apply {
        onEach { log("new event: $it") }
    }

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
        viewModelScope.launch { _uiState.emit(state) }
    }

    /**
     * Emits a one-shot event
     */
    protected fun emitEvent(event: MVI.Event) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

    public fun <P : MVI.State> reduceAndPublishState(reducer: MVI.Reducer<S, P>, partial: P) {
        viewModelScope.launch {
            _uiState.emit(reducer.reduce(currentState, partial))
        }
    }

    protected abstract suspend fun processIntent(intent: I)

    final override fun sendIntent(intent: I) {
        log("new intent: $intent")
        viewModelScope.launch {
            processIntent(intent)
        }
    }

    private fun log(message: String) = Log.d(this::class.java.simpleName, message)
}

public object MVI {

    public interface State

    public interface Intent

    public interface Event

    public interface ViewModel<S : State, I : Intent> {

        public fun sendIntent(intent: I)
    }

    public fun interface Reducer<S : State, P : State> {
        public fun reduce(previousState: S, partialState: P): S
    }
}
