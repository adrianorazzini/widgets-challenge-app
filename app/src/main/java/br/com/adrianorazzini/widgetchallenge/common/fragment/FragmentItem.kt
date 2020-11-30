package br.com.adrianorazzini.widgetchallenge.common.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.adrianorazzini.widgetchallenge.common.databinding.BaseViewModel
import br.com.adrianorazzini.widgetchallenge.common.databinding.BaseViewState

abstract class FragmentItem<VM : BaseViewModel<VS>, VS : BaseViewState> : Fragment() {

    companion object {
        private const val LOG_TAG = "FragmentItem"
    }

    lateinit var mViewModel: VM
    lateinit var mBinding: ViewDataBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(mViewModel)
        mViewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            updateViewStateSuccess(viewState)
        })
    }

    open fun updateViewStateSuccess(viewState: VS) {
        Log.d(LOG_TAG, "::updateViewStateSuccess:: - State: $viewState")
        // Implemented by child
    }

    open fun updateViewStateError(throwable: Throwable?) {
        Log.e(LOG_TAG, throwable?.message ?: "Error to update view state!")
        // Implemented by child
    }
}