package com.dongyang.android.youdongknowme.ui.view.setting

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.os.Build
import androidx.core.content.ContextCompat
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityOnboardingPermissionBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.ui.view.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingPermissionActivity :
    BaseActivity<ActivityOnboardingPermissionBinding, SettingViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_onboarding_permission
    override val viewModel: SettingViewModel by viewModel()

    override fun initStartView() {

        setPermissionSwitch(false)
        setSpanText(binding.tvPermissionTitleMain, startIdx = 0, endIdx = 9)

        binding.btnPermissionComplete.setOnClickListener {
            val intent = MainActivity.createIntent(this@OnboardingPermissionActivity)
            startActivity(intent)
            finish()
        }
    }

    override fun initDataBinding() = Unit

    override fun initAfterBinding() {
        binding.switchPermission.setOnCheckedChangeListener { compoundButton, _ ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (compoundButton.isChecked) {
                    if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
                            this, Manifest.permission.POST_NOTIFICATIONS
                        )
                    ) {
                        setPermissionSwitch(true)
                    } else {
                        val dialog = PermissionDialog(
                            getString(R.string.dialog_permission_title),
                            getString(R.string.dialog_permission_content),
                            this.packageName,
                            cancelListener = { setPermissionSwitch(false) }
                        )
                        dialog.show(supportFragmentManager, "CustomDialog")
                    }
                } else {
                    setPermissionSwitch(false)
                }
            } else {
                if (compoundButton.isChecked) {
                    setPermissionSwitch(true)
                } else {
                    setPermissionSwitch(false)
                }
            }
        }
    }

    private fun setPermissionSwitch(isChecked: Boolean) {
        val resources = if (isChecked) {
            R.color.blue300
        } else {
            R.color.gray400
        }

        binding.switchPermission.isChecked = isChecked
        binding.switchPermission.compoundDrawableTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, resources))
        binding.switchPermission.setTextColor(getColor(resources))
        binding.mvSwitchPermission.strokeColor = getColor(resources)
        viewModel.setIsAccessUniversityAlarm(isChecked)
        viewModel.setIsAccessDepartAlarm(isChecked)
    }

    override fun onResume() {
        super.onResume()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                setPermissionSwitch(true)
            }
        }
    }
}