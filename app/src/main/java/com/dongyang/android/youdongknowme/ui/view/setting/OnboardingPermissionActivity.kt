package com.dongyang.android.youdongknowme.ui.view.setting

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityOnboardingPermissionBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.ui.view.main.MainActivity
import com.dongyang.android.youdongknowme.ui.view.setting.SettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingPermissionActivity :
    BaseActivity<ActivityOnboardingPermissionBinding, SettingViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_onboarding_permission
    override val viewModel: SettingViewModel by viewModel()

    override fun initStartView() {
        viewModel.checkAccessAlarm()
        viewModel.getUserDepartment()
        setPermissionSwitch(false)

        setSpanText(binding.tvPermissionTitleMain, startIdx = 0, endIdx = 9)
    }

    override fun initDataBinding() = Unit

    override fun initAfterBinding() {
        binding.btnPermissionComplete.setOnClickListener {
            val intent = MainActivity.createIntent(this@OnboardingPermissionActivity)
            startActivity(intent)
            finish()
        }

        binding.switchPermission.setOnCheckedChangeListener { compoundButton, _ ->
            if (compoundButton.isChecked) {
                // 권환 확인 전 스위치 초기화
                binding.switchPermission.isChecked = false

                // 온보딩 알림 스위치를  활성화
                if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
                        this, Manifest.permission.POST_NOTIFICATIONS
                    )
                ) {
                    // 알림 권한이 허용 상태
                    setPermissionSwitch(true)
                } else {
                    // 알림 권한이 미허용 상태
                    val intent =
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + this.packageName))
                    startActivity(intent)
                }
            } else {
                // 온보딩 알림 스위치 비활성화
                setPermissionSwitch(false)
            }
        }
    }

    private fun setPermissionSwitch(isChecked: Boolean) {
        val resources = if (isChecked) {
            R.color.blue300
        } else {
            R.color.gray400
        }

        binding.switchPermission.compoundDrawableTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, resources))
        binding.switchPermission.setTextColor(getColor(resources))
        binding.mvSwitchPermission.strokeColor = getColor(resources)

        binding.switchPermission.isChecked = isChecked
        viewModel.setIsAccessUniversityAlarm(isChecked)
        viewModel.setIsAccessDepartAlarm(isChecked)
    }
}