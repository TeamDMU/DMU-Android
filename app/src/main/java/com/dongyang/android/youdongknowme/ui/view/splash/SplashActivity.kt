package com.dongyang.android.youdongknowme.ui.view.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
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
class SplashActivity() : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

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

            if (viewModel.isFirstLaunch.value == true) {
                val intent = Intent(this@SplashActivity, OnboardingDepartActivity::class.java)

                if (PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(
                        this@SplashActivity, Manifest.permission.POST_NOTIFICATIONS
                    )
                ) {
                    // 권한 설정이 안되어 있는 경우
                    ActivityCompat.requestPermissions(
                        this@SplashActivity, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 100
                    )
                } else {
                    // 권한 설정이 이미 되어 있는 경우
                    startActivity(intent)
                    finish()
                }
            } else {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            // 권한 설정 결과 처리
            val intent = if (viewModel.isFirstLaunch.value == true) {
                Intent(this@SplashActivity, OnboardingDepartActivity::class.java)
            } else {
                Intent(this@SplashActivity, MainActivity::class.java)
            }
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
    }
}