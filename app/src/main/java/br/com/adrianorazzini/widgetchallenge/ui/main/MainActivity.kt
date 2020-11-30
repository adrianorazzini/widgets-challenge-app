package br.com.adrianorazzini.widgetchallenge.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.adrianorazzini.widgetchallenge.R
import br.com.adrianorazzini.widgetchallenge.databinding.MainActivityBinding
import br.com.adrianorazzini.widgetchallenge.ui.dialog.*

class MainActivity : AppCompatActivity(), DefaultAlertDialog.InteractionDialogListener {

    private val mHandler = Handler(Looper.getMainLooper())

    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: MainActivityBinding
    private lateinit var mProgressDialog: DefaultProgressDialog

    private var mIsPaused: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mBinding.viewModel = mViewModel
        mBinding.executePendingBindings()

        mProgressDialog = DefaultProgressDialog()

        // Get the NavController for your NavHostFragment
        val navController = findNavController(R.id.mainNavHostFragment)
        setupActionBarWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()

        mIsPaused = false
    }

    override fun onPause() {
        super.onPause()

        mIsPaused = true

        hideProgressDialog(0)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.mainNavHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onPositiveClicked(dialog: DefaultAlertDialog) {
        when (dialog.tag) {
            GENERIC_ERROR_DIALOG_TAG, INVALID_CARD_ID_DIALOG_TAG, INVALID_ACCOUNT_ID_DIALOG_TAG -> {
                val navController = findNavController(R.id.mainNavHostFragment)
                navController.popBackStack()
            }
        }
    }

    override fun onNegativeClicked(dialog: DefaultAlertDialog) {
        // ignore
    }

    override fun onDialogCancelled(dialog: DefaultAlertDialog) {
        // ignore
    }

    override fun onDialogDismissed(dialog: DefaultAlertDialog) {
        // ignore
    }

    fun showProgressDialog() {
        if (isActivityAvailable() && !mProgressDialog.isResumed
            && !mProgressDialog.isAdded && !mProgressDialog.isVisible
        ) {
            mProgressDialog.show(
                supportFragmentManager,
                DefaultProgressDialog::class.java.simpleName
            )
            supportFragmentManager.executePendingTransactions()
        }
    }

    fun hideProgressDialog(delay: Long) {
        val delayByState = if (isActivityAvailable()) delay else 0
        if (delayByState > 0) {
            mHandler.postDelayed(this::definitivelyDismissProgressDialog, delayByState)
        } else {
            definitivelyDismissProgressDialog()
        }
    }

    private fun definitivelyDismissProgressDialog() {
        if (isActivityAvailable() && (mProgressDialog.fragmentManager != null)) {
            mProgressDialog.dismissAllowingStateLoss()
        }
    }

    private fun isActivityAvailable(): Boolean {
        return !mIsPaused && !isChangingConfigurations && !isFinishing && !isDestroyed
    }
}
