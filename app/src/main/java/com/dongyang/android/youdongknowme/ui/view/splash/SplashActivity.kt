package com.dongyang.android.youdongknowme.ui.view.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_splash
    override val viewModel: SplashViewModel by viewModel()

    private var intentJob: Job? = null

    override fun initStartView() {
        viewModel.checkFirstLaunch()
    }

    override fun initDataBinding() = Unit

    override fun initAfterBinding() {
        intentJob = lifecycleScope.launch {
            delay(SPLASH_TIME_MILLIS)
        }

        if (viewModel.isFirstLaunch.value == true) {
            permissionCheck()
        } else {
            val intent = MainActivity.createIntent(this@SplashActivity)
            startActivity(intent)
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_PERMISSION_CODE) {
            val intent = OnboardingDepartActivity.createIntent(this@SplashActivity)
            startActivity(intent)
            finish()
        }
    }

    private fun permissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(
                    this@SplashActivity, Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                ActivityCompat.requestPermissions(
                    this@SplashActivity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_PERMISSION_CODE
                )
            } else {
                val intent = OnboardingDepartActivity.createIntent(this@SplashActivity)
                startActivity(intent)
                finish()
            }
        } else {
            val intent = OnboardingDepartActivity.createIntent(this@SplashActivity)
            startActivity(intent)
            finish()
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
        private const val REQUEST_PERMISSION_CODE = 100
    }
}