package com.dongyang.android.youdongknowme.ui.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityMainBinding
import com.dongyang.android.youdongknowme.ui.view.cafeteria.CafeteriaFragment
import com.dongyang.android.youdongknowme.ui.view.notice.NoticeFragment
import com.dongyang.android.youdongknowme.ui.view.schedule.ScheduleFragment
import com.dongyang.android.youdongknowme.ui.view.setting.SettingFragment
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/* 메인 액티비티 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        initDataBinding()
        initBottomNavigation()
        if (savedInstanceState == null) {
            binding.mainNvBottom.selectedItemId =
                R.id.noticeFragment
        }
    }

    private fun initDataBinding() {
        viewModel.isFirstLaunch.observe(this) { boolean ->
            if (boolean) getFcmToken()
        }
    }

    private fun getFcmToken() {
        viewModel.setIsFirstLaunch(false)
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                viewModel.setFCMToken(token).run { viewModel.setInitToken() }
            } else {
                Timber.e("토큰 재발급 실패 : ${task.exception}")
            }
        }
    }

    private fun initBottomNavigation() {
        binding.mainNvBottom.setOnItemSelectedListener { item ->
            return@setOnItemSelectedListener when (item.itemId) {
                R.id.noticeFragment -> {
                    replaceFragment<NoticeFragment>()
                    true
                }

                R.id.scheduleFragment -> {
                    replaceFragment<ScheduleFragment>()
                    true
                }

                R.id.cafeteriaFragment -> {
                    replaceFragment<CafeteriaFragment>()
                    true
                }

                R.id.settingFragment -> {
                    replaceFragment<SettingFragment>()
                    true
                }

                else -> false
            }
        }
    }

    private inline fun <reified T : Fragment> replaceFragment() {
        val tag: String = T::class.java.name
        val fragment =
            supportFragmentManager.findFragmentByTag(tag) as? T ?: T::class.java.newInstance()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            supportFragmentManager.fragments.forEach { hide(it) }
            if (fragment.isAdded) {
                show(fragment)
            } else {
                add(binding.mainNavContainer.id, fragment, tag)
            }
        }
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
