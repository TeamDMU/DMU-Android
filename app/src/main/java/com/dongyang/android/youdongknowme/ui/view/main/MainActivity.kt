package com.dongyang.android.youdongknowme.ui.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityMainBinding
import com.dongyang.android.youdongknowme.ui.view.notice.NoticeFragment
import com.dongyang.android.youdongknowme.ui.view.schedule.ScheduleFragment
import com.dongyang.android.youdongknowme.ui.view.setting.SettingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


/* 메인 액티비티 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    enum class Tab {
        NOTICE,
        SCHEDULE,
        SETTING
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initNavigationView()

        viewModel.currentFragmentType.observe(this) {
            changeFragment(it)
        }
    }

    private fun initNavigationView() {
        binding.mainNvBottom.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_main_home -> viewModel.setCurrentFragment(Tab.NOTICE)
                    R.id.menu_main_schedule -> viewModel.setCurrentFragment(Tab.SCHEDULE)
                    R.id.menu_main_setting -> viewModel.setCurrentFragment(Tab.SETTING)
                    else -> throw IllegalArgumentException("메뉴 아이템을 찾을 수 없습니다.")
                }
                true
            }
        }
    }

    private fun changeFragment(tab: Tab) {
        val transaction = supportFragmentManager.beginTransaction()
        var target = supportFragmentManager.findFragmentByTag(tab.name)

        if (target == null) {
            target = getFragment(tab)
            transaction.add(R.id.main_container, target, tab.name)
        }

        transaction.show(target)

        Tab.values()
            .filterNot { it == tab }
            .forEach { type ->
                supportFragmentManager.findFragmentByTag(type.name)?.let { it ->
                    transaction.hide(it)
                }
            }

        transaction.commitAllowingStateLoss()
    }

    private fun getFragment(tab: Tab): Fragment {
        return when (tab) {
            Tab.NOTICE -> NoticeFragment.newInstance()
            Tab.SCHEDULE -> ScheduleFragment.newInstance()
            Tab.SETTING -> SettingFragment.newInstance()
        }
    }
}