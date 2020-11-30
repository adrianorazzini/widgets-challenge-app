package br.com.adrianorazzini.widgetchallenge.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.adrianorazzini.widgetchallenge.R
import br.com.adrianorazzini.widgetchallenge.databinding.MainActivityBinding
import br.com.adrianorazzini.widgetchallenge.ui.dialog.DefaultAlertDialog
import br.com.adrianorazzini.widgetchallenge.ui.dialog.GENERIC_ERROR_DIALOG_TAG
import br.com.adrianorazzini.widgetchallenge.ui.dialog.INVALID_CARD_ID_DIALOG_TAG

class MainActivity : AppCompatActivity(), DefaultAlertDialog.InteractionDialogListener {

    override fun onPositiveClicked(dialog: DefaultAlertDialog) {
        when (dialog.tag) {
            GENERIC_ERROR_DIALOG_TAG, INVALID_CARD_ID_DIALOG_TAG -> {
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

    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: MainActivityBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mBinding.viewModel = mViewModel
        mBinding.executePendingBindings()

        // Get the NavController for your NavHostFragment
        val navController = findNavController(R.id.mainNavHostFragment)
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.mainNavHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
