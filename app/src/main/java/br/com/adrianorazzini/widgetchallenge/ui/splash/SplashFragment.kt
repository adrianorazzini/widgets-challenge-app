package br.com.adrianorazzini.widgetchallenge.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import br.com.adrianorazzini.widgetchallenge.R
import br.com.adrianorazzini.widgetchallenge.common.fragment.FragmentItem
import br.com.adrianorazzini.widgetchallenge.databinding.SplashFragmentBinding
import br.com.adrianorazzini.widgetchallenge.ui.main.MainActivity
import br.com.adrianorazzini.widgetchallenge.ui.main.MainViewModel
import br.com.adrianorazzini.widgetchallenge.ui.main.MainViewState
import kotlinx.android.synthetic.main.splash_fragment.*
import java.util.concurrent.TimeUnit

class SplashFragment : FragmentItem<MainViewModel, MainViewState>() {

    private val mHandler = Handler(Looper.getMainLooper())

    companion object {
        private val SPLASH_DELAY = TimeUnit.SECONDS.toMillis(3)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.splash_fragment,
            container, false
        )

        mViewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        (mBinding as SplashFragmentBinding).viewModel = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.executePendingBindings()
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).supportActionBar?.hide()

        animateLogo()
        mHandler.postDelayed({
            val options = NavOptions
                .Builder()
                .setPopUpTo(R.id.splashFragment, true)
                .build()
            findNavController()
                .navigate(R.id.action_splashFragment_to_homeFragment, null, options)
        }, SPLASH_DELAY)
    }

    override fun onPause() {
        super.onPause()

        mHandler.removeCallbacksAndMessages(null)
    }

    private fun animateLogo() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.splash_animation)
        animation.interpolator = LinearInterpolator()
        animation.repeatCount = Animation.INFINITE
        animation.duration = TimeUnit.SECONDS.toMillis(1)

        splash_logo.startAnimation(animation)
    }
}