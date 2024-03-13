package com.dongyang.android.youdongknowme.ui.view.permission

import android.content.Intent
import android.os.Build
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityOnboardingPermissionBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.ui.view.main.MainActivity
import com.dongyang.android.youdongknowme.ui.view.setting.SettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingPermissionActivity : BaseActivity<ActivityOnboardingPermissionBinding, SettingViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_onboarding_permission
    override val viewModel: SettingViewModel by viewModel()

    override fun initStartView() {
        viewModel.setIsAccessSchoolAlarm(false)
        viewModel.setIsAccessDepartAlarm(false)

        viewModel.checkAccessAlarm()
        viewModel.getUserDepartment()

        setSpanText(this, binding.tvPermissionTitleMain,startIdx = 0, endIdx = 9)
    }

    override fun initDataBinding() = Unit

    override fun initAfterBinding() {
        binding.btnPermissionComplete.setOnClickListener {
            val intent = Intent(this@OnboardingPermissionActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.switchPermission.setOnCheckedChangeListener { compoundButton, _ ->
            if (compoundButton.isChecked) {
                viewModel.setIsAccessSchoolAlarm(true)
                viewModel.setIsAccessDepartAlarm(true)
                binding.switchPermission.setTextColor(getColor(R.color.blue300))
            } else {
                viewModel.setIsAccessSchoolAlarm(false)
                viewModel.setIsAccessDepartAlarm(false)
                binding.switchPermission.setTextColor(getColor(R.color.gray300))
            }
        }
    }
}