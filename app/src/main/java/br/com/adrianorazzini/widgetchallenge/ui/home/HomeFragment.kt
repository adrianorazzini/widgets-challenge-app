package br.com.adrianorazzini.widgetchallenge.ui.home

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import br.com.adrianorazzini.remote.model.Identifier
import br.com.adrianorazzini.widgetchallenge.R
import br.com.adrianorazzini.widgetchallenge.common.decoration.GridSpacingItemDecoration
import br.com.adrianorazzini.widgetchallenge.common.fragment.FragmentItem
import br.com.adrianorazzini.widgetchallenge.databinding.HomeFragmentBinding
import br.com.adrianorazzini.widgetchallenge.ui.dialog.showSimpleAlertDialog
import br.com.adrianorazzini.widgetchallenge.ui.home.adapter.CardListAdapter
import br.com.adrianorazzini.widgetchallenge.ui.home.listener.CardButtonClickListener
import br.com.adrianorazzini.widgetchallenge.ui.main.MainActivity
import br.com.adrianorazzini.widgetchallenge.ui.main.MainViewModel
import br.com.adrianorazzini.widgetchallenge.ui.main.MainViewState
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : FragmentItem<MainViewModel, MainViewState>(), CardButtonClickListener {

    private lateinit var mRecyclerAdapter: CardListAdapter

    companion object {
        private const val LOG_TAG = "HomeFragment"
        private const val SPAN_COUNT = 1
        private const val SPACING_DIMENSION = 8f
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.home_fragment,
            container, false
        )

        mViewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        (mBinding as HomeFragmentBinding).viewModel = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.executePendingBindings()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val space = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, SPACING_DIMENSION,
            resources.displayMetrics
        ).toInt()
        val gridManager = GridLayoutManager(activity, SPAN_COUNT)
        mRecyclerAdapter = CardListAdapter(this)
        homeRecyclerView.layoutManager = gridManager
        homeRecyclerView.addItemDecoration(GridSpacingItemDecoration(SPAN_COUNT, space, true))
        homeRecyclerView.itemAnimator = DefaultItemAnimator()
        homeRecyclerView.adapter = mRecyclerAdapter
    }

    override fun onStart() {
        super.onStart()

        (activity as MainActivity).supportActionBar?.hide()
        mViewModel.loadWidgets()
    }

    override fun onPause() {
        super.onPause()

        mDisposableContainer.clear()
    }

    override fun onButtonClick(view: View, position: Int) {
        if (view.id == R.id.cardButton) {
            val tag = view.tag
            if (tag is String) {
                when {
                    Identifier.fromString(tag) == Identifier.HOME_CARD_WIDGET -> {
                        mViewModel.setSelectedCardId(position)
                        findNavController()
                            .navigate(R.id.action_homeFragment_to_cardInfoFragment)
                    }
                    Identifier.fromString(tag) == Identifier.HOME_STATEMENT_WIDGET -> {
                        mViewModel.setSelectedAccountId(position)
                        findNavController()
                            .navigate(R.id.action_homeFragment_to_statementFragment)
                    }
                    else -> {
                        (activity as MainActivity).showSimpleAlertDialog(
                            getString(R.string.home_error_no_action_associated_title),
                            getString(R.string.home_error_no_action_associated_message),
                            cancelable = true
                        )
                    }
                }
            }
        }
    }

    override fun updateViewStateSuccess(viewState: MainViewState) {
        viewState.cardItems?.let {
            mRecyclerAdapter.submitList(it)
            mRecyclerAdapter.notifyDataSetChanged()
        }
    }

    override fun updateViewStateError(throwable: Throwable?) {
        Log.e(LOG_TAG, throwable?.message ?: "Error to update view state!")
    }
}