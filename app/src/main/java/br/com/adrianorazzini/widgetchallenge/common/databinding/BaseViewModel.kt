package br.com.adrianorazzini.widgetchallenge.common.databinding

import androidx.lifecycle.*
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<STATE : BaseViewState> : ViewModel(), LifecycleObserver {

    private val _viewState: MutableLiveData<STATE> = MutableLiveData()
    val viewState = _viewState

    protected val disposableContainer = CompositeDisposable()

    open fun onAttach() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
        disposableContainer.clear()
    }

    protected fun updateViewState(viewState: STATE) {
        _viewState.postValue(viewState)
    }

    override fun onCleared() {
        super.onCleared()
        disposableContainer.clear()
    }
}