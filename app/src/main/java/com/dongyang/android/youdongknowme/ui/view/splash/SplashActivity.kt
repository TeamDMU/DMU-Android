package com.dongyang.android.youdongknowme.ui.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivitySplashBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.ui.view.depart.OnboardingDepartActivity
import com.dongyang.android.youdongknowme.ui.view.main.MainActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity () :
    BaseActivity<ActivitySplashBinding, SplashViewModel>()  {

    override val layoutResourceId: Int = R.layout.activity_splash
    override val viewModel: SplashViewModel by viewModel()

     private var intentJob: Job? = null

    override fun initStartView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val content: View = findViewById(android.R.id.content)
            content.viewTreeObserver.addOnPreDrawListener { false }
        }

        viewModel.checkFirstLaunch()
    }

    override fun initDataBinding() = Unit

    override fun initAfterBinding() {
        intentJob = lifecycleScope.launch {
            delay(SPLASH_TIME_MILLIS)

            if(viewModel.isFirstLaunch) {
                val intent = Intent(this@SplashActivity, OnboardingDepartActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        intentJob?.cancel()
        super.onBackPressed()
    }

    override fun onDestroy() {
        intentJob?.cancel()
        super.onDestroy()
    }

    companion object {
        private const val SPLASH_TIME_MILLIS = 1_500L
    }
}