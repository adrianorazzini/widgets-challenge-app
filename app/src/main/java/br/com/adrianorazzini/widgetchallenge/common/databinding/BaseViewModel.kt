package br.com.adrianorazzini.widgetchallenge.common.databinding

import androidx.lifecycle.*

abstract class BaseViewModel<STATE : BaseViewState> : ViewModel(), LifecycleObserver {

    private val _viewState: MutableLiveData<STATE> = MutableLiveData()
    val viewState = _viewState

    open fun onAttach() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
    }

    protected fun updateViewState(viewState: STATE) {
        _viewState.postValue(viewState)
    }
}