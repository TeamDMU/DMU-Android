package com.dongyang.android.youdongknowme.ui.view.settingAlarm

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivitySettingAlarmBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.ui.view.main.MainActivity
import com.dongyang.android.youdongknowme.ui.view.setting.SettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingAlarmActivity : BaseActivity<ActivitySettingAlarmBinding, SettingViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_setting_alarm
    override val viewModel: SettingViewModel by viewModel()

    override fun initStartView() {
    }

    override fun initDataBinding() {}

    @RequiresApi(Build.VERSION_CODES.P)
    override fun initAfterBinding() {
        binding.btnAlarmComplete.setOnClickListener {
            val intent = Intent(this@SettingAlarmActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.switchAlarm.setOnCheckedChangeListener { compoundButton, _ ->
            if (compoundButton.isChecked) {
                viewModel.setIsAccessDepartAlarm(true)
                binding.mvSwitchAlarm.outlineAmbientShadowColor = getColor(R.color.blue300)
                binding.mvSwitchAlarm.outlineSpotShadowColor = getColor(R.color.blue300)
                binding.switchAlarm.setTextColor(getColor(R.color.blue300))
            } else {
                viewModel.setIsAccessDepartAlarm(false)
                binding.mvSwitchAlarm.outlineAmbientShadowColor = getColor(R.color.gray300)
                binding.mvSwitchAlarm.outlineSpotShadowColor = getColor(R.color.gray300)
                binding.switchAlarm.setTextColor(getColor(R.color.gray300))
            }
        }
    }
}