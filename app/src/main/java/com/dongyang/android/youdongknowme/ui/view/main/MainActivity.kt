package com.dongyang.android.youdongknowme.ui.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityMainBinding
import com.dongyang.android.youdongknowme.ui.view.notice.NoticeFragment
import com.dongyang.android.youdongknowme.ui.view.schedule.ScheduleFragment
import com.dongyang.android.youdongknowme.ui.view.setting.SettingFragment


/* 메인 액티비티 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, NoticeFragment.newInstance())
            .commit()

        initNavigationView()
    }

    private fun initNavigationView() {
        binding.mainNvBottom.run {
            setOnItemSelectedListener { item ->
                replaceFragment(
                    when (item.itemId) {
                        R.id.menu_main_home -> NoticeFragment.newInstance()
                        R.id.menu_main_setting -> SettingFragment.newInstance()
                        else -> ScheduleFragment.newInstance()
                    }
                )
                true
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }
}