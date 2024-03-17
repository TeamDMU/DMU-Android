package com.dongyang.android.youdongknowme.ui.view.permission

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityOnboardingPermissionBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.ui.view.main.MainActivity
import com.dongyang.android.youdongknowme.ui.view.setting.SettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingPermissionActivity() :
    BaseActivity<ActivityOnboardingPermissionBinding, SettingViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_onboarding_permission
    override val viewModel: SettingViewModel by viewModel()

    override fun initStartView() {
        viewModel.checkAccessAlarm()
        viewModel.getUserDepartment()
        setPermissionSwitch(false)

        setSpanText(this, binding.tvPermissionTitleMain, startIdx = 0, endIdx = 9)
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
                if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
                        this, Manifest.permission.POST_NOTIFICATIONS
                    )
                ) {
                    setPermissionSwitch(true)
                } else {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 100
                    )
                }
            } else {
                setPermissionSwitch(false)
            }
        }
    }

    private fun setPermissionSwitch(isChecked: Boolean) {
        val resources = if (isChecked) {
            R.color.blue300
        } else {
            R.color.gray300
        }

        binding.switchPermission.compoundDrawableTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, resources))
        binding.switchPermission.setTextColor(getColor(resources))
        binding.mvSwitchPermission.strokeColor = getColor(resources)

        binding.switchPermission.isChecked = isChecked
        viewModel.setIsAccessSchoolAlarm(isChecked)
        viewModel.setIsAccessDepartAlarm(isChecked)
    }
}