package br.com.adrianorazzini.widgetchallenge.ui.statement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.adrianorazzini.widgetchallenge.R
import br.com.adrianorazzini.widgetchallenge.common.fragment.BaseFragment
import br.com.adrianorazzini.widgetchallenge.databinding.StatementFragmentBinding
import br.com.adrianorazzini.widgetchallenge.ui.dialog.showGenericErrorDialog
import br.com.adrianorazzini.widgetchallenge.ui.dialog.showInvalidAccountIdDialog
import br.com.adrianorazzini.widgetchallenge.ui.main.MainActivity
import br.com.adrianorazzini.widgetchallenge.ui.main.MainViewModel
import br.com.adrianorazzini.widgetchallenge.ui.main.MainViewState
import br.com.adrianorazzini.widgetchallenge.ui.main.StateError
import br.com.adrianorazzini.widgetchallenge.ui.statement.adapter.StatementListAdapter
import kotlinx.android.synthetic.main.statement_fragment.*

class StatementFragment : BaseFragment<MainViewModel, MainViewState>() {

    private lateinit var mRecyclerAdapter: StatementListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.statement_fragment,
            container, false
        )

        mViewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        (mBinding as StatementFragmentBinding).viewModel = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.executePendingBindings()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclerAdapter = StatementListAdapter()
        statementRecyclerView.layoutManager = LinearLayoutManager(context)
        statementRecyclerView.itemAnimator = DefaultItemAnimator()
        statementRecyclerView.adapter = mRecyclerAdapter
    }

    override fun onStart() {
        super.onStart()

        (activity as MainActivity).supportActionBar?.show()
        (activity as MainActivity).showProgressDialog()
        mViewModel.loadStatementInfo()
    }

    override fun onPause() {
        super.onPause()

        mViewModel.clearStatementInfo()
    }

    override fun updateViewStateSuccess(viewState: MainViewState) {
        viewState.transactionItems?.let {
            mRecyclerAdapter.submitList(it)
            mRecyclerAdapter.notifyDataSetChanged()

            (activity as MainActivity).hideProgressDialog(0)
        }

        viewState.error?.let {
            (activity as MainActivity).hideProgressDialog(0)

            if (it == StateError.INVALID_ACCOUNT_ID) {
                (activity as MainActivity).showInvalidAccountIdDialog()
            } else if (it == StateError.INVALID_STATEMENT_INFO) {
                (activity as MainActivity).showGenericErrorDialog()
            }
        }
    }
}