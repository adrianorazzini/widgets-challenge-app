package br.com.adrianorazzini.widgetchallenge.ui.card_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import br.com.adrianorazzini.widgetchallenge.R
import br.com.adrianorazzini.widgetchallenge.common.fragment.FragmentItem
import br.com.adrianorazzini.widgetchallenge.databinding.CardInfoFragmentBinding
import br.com.adrianorazzini.widgetchallenge.ui.dialog.showGenericErrorDialog
import br.com.adrianorazzini.widgetchallenge.ui.dialog.showInvalidCardIdDialog
import br.com.adrianorazzini.widgetchallenge.ui.main.MainActivity
import br.com.adrianorazzini.widgetchallenge.ui.main.MainViewModel
import br.com.adrianorazzini.widgetchallenge.ui.main.MainViewState
import br.com.adrianorazzini.widgetchallenge.ui.main.StateError

class CardInfoFragment : FragmentItem<MainViewModel, MainViewState>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.card_info_fragment,
            container, false
        )

        mViewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        (mBinding as CardInfoFragmentBinding).viewModel = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.executePendingBindings()
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()

        (activity as MainActivity).supportActionBar?.show()
        mViewModel.loadCardInfo()
    }

    override fun onPause() {
        super.onPause()

        mViewModel.clearCardInfo()
        mDisposableContainer.clear()
    }

    override fun updateViewStateSuccess(viewState: MainViewState) {
        viewState.error?.let {
            if (it == StateError.INVALID_CARD_ID) {
                (activity as MainActivity).showInvalidCardIdDialog()
            } else if (it == StateError.INVALID_CARD_INFO) {
                (activity as MainActivity).showGenericErrorDialog()
            }
        }
    }
}