package com.dongyang.android.youdongknowme.ui.view.settingAlarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.plusAssign
import androidx.navigation.ui.setupWithNavController
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivitySettingAlarmBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.ui.view.keyword.KeywordViewModel
import com.dongyang.android.youdongknowme.ui.view.util.KeepStateNavigator
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingAlarmActivity : BaseActivity<ActivitySettingAlarmBinding, SettingAlarmViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_setting_alarm
    override val viewModel: SettingAlarmViewModel by viewModel()

    override fun initStartView() {
    }

    override fun initDataBinding() {}

    override fun initAfterBinding() {}
}