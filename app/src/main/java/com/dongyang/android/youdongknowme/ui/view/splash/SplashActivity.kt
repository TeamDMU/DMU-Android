package com.dongyang.android.youdongknowme.ui.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dongyang.android.youdongknowme.databinding.ActivitySplashBinding
import com.dongyang.android.youdongknowme.standard.util.logd
import com.dongyang.android.youdongknowme.ui.view.depart.DepartActivity
import com.dongyang.android.youdongknowme.ui.view.main.MainActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var intentJob: Job? = null
    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.checkDepart()

        intentJob = lifecycleScope.launch {
            delay(2000L)

            viewModel.isDepart.observe(this@SplashActivity) {
                if (!it) {
                    logd("학과 정보 없음")
                    val intent = Intent(this@SplashActivity, DepartActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    logd("학과 정보 있음")
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
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
}