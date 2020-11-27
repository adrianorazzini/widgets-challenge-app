package br.com.adrianorazzini.widgetchallenge.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import br.com.adrianorazzini.widgetchallenge.R
import br.com.adrianorazzini.widgetchallenge.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: MainActivityBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mBinding.viewModel = mViewModel
        mBinding.executePendingBindings()
    }
}
