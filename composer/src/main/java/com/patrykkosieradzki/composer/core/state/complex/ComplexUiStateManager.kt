package com.patrykkosieradzki.composer.core.state.complex

import com.patrykkosieradzki.composer.core.state.UiStateManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

interface ComplexUiStateManager<DATA : Any> : UiStateManager<ComplexUiState<DATA>> {
    val currentStateData: DATA

    fun updateComplexUiState(updateFunc: (ComplexUiState<DATA>) -> ComplexUiState<DATA>)
    fun updateComplexUiStateToSuccess(updateFunc: (DATA) -> DATA)
    fun updateComplexUiStateToFailure(error: Throwable, updateFunc: ((DATA) -> DATA)? = null)
    fun updateComplexUiStateToSwipeRefreshFailure(error: Throwable, updateFunc: ((DATA) -> DATA)? = null)
}

class ComplexUiStateManagerImpl<DATA : Any>(
    override val initialState: ComplexUiState<DATA>,
    dataKClass: KClass<DATA>
) : ComplexUiStateManager<DATA> {
    private val _uiState: MutableStateFlow<ComplexUiState<DATA>> by lazy {
        MutableStateFlow(initialState)
    }
    override val uiState = _uiState.asStateFlow()
    override val currentState: ComplexUiState<DATA>
        get() = uiState.value
    override val currentStateData
        get() = uiState.value.getData()

    init {
        if (!dataKClass.isData) {
            throw IllegalArgumentException("DATA has to be a data class!")
        }
    }

    override fun updateComplexUiState(updateFunc: (ComplexUiState<DATA>) -> ComplexUiState<DATA>) {
        _uiState.update(updateFunc)
    }

    override fun updateUiStateToLoading(doBefore: (() -> Unit)?) {
        doBefore?.let { it() }
        updateComplexUiState { ComplexUiState.Loading(uiState.value.getData()) }
    }

    override fun updateUiStateToRetrying(doBefore: (() -> Unit)?) {
        doBefore?.let { it() }
        updateComplexUiState { ComplexUiState.Loading(uiState.value.getData()) }
    }

    override fun updateUiStateToSwipeRefreshing(doBefore: (() -> Unit)?) {
        doBefore?.let { it() }
        updateComplexUiState { ComplexUiState.Loading(uiState.value.getData()) }
    }

    override fun updateComplexUiStateToSuccess(updateFunc: (DATA) -> DATA) {
        val newStateData = updateFunc.invoke(uiState.value.getData())
        updateComplexUiState { ComplexUiState.Success(newStateData) }
    }

    override fun updateComplexUiStateToFailure(
        error: Throwable,
        updateFunc: ((DATA) -> DATA)?
    ) {
        val newStateData = updateFunc?.invoke(uiState.value.getData()) ?: uiState.value.getData()
        updateComplexUiState { ComplexUiState.Failure(newStateData, error) }
    }

    override fun updateComplexUiStateToSwipeRefreshFailure(
        error: Throwable,
        updateFunc: ((DATA) -> DATA)?
    ) {
        val newStateData = updateFunc?.invoke(uiState.value.getData()) ?: uiState.value.getData()
        updateComplexUiState { ComplexUiState.SwipeRefreshFailure(newStateData, error) }
    }

    override fun updateUiStateToSuccess(doBefore: (() -> Unit)?) {
        doBefore?.let { it() }
        updateComplexUiState { ComplexUiState.Success(currentStateData) }
    }

    override fun updateUiStateToFailure(error: Throwable, doBefore: (() -> Unit)?) {
        doBefore?.let { it() }
        updateComplexUiState { ComplexUiState.Failure(currentStateData, error) }
    }

    override fun updateUiStateToSwipeRefreshFailure(error: Throwable, doBefore: (() -> Unit)?) {
        doBefore?.let { it() }
        updateComplexUiState { ComplexUiState.SwipeRefreshFailure(currentStateData, error) }
    }
}
