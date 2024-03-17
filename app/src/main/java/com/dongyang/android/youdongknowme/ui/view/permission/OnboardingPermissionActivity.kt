package com.dongyang.android.youdongknowme.ui.view.permission

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityOnboardingPermissionBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.ui.view.main.MainActivity
import com.dongyang.android.youdongknowme.ui.view.setting.SettingViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingPermissionActivity :
    BaseActivity<ActivityOnboardingPermissionBinding, SettingViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_onboarding_permission
    override val viewModel: SettingViewModel by viewModel()
    val permission = Manifest.permission.POST_NOTIFICATIONS


    override fun initStartView() {
        viewModel.checkAccessAlarm()
        viewModel.getUserDepartment()

        setSpanText(this, binding.tvPermissionTitleMain, startIdx = 0, endIdx = 9)
    }

    override fun initDataBinding() = Unit

    override fun initAfterBinding() {
        binding.btnPermissionComplete.setOnClickListener {
            val intent = Intent(this@OnboardingPermissionActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        setPermissionSwitch(false)

        binding.switchPermission.setOnCheckedChangeListener { compoundButton, _ ->
            val isPermissionGranted = isPermissionGranted(this@OnboardingPermissionActivity)

            if (compoundButton.isChecked) {
                if (isPermissionGranted) {
                    setPermissionSwitch(true)
                } else {
                    requestPermission(0)
                    setPermissionSwitch(false)
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

    private fun isPermissionGranted(activity: Activity): Boolean =
        ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission(requestCode: Int) =
        ActivityCompat.requestPermissions(
            this@OnboardingPermissionActivity,
            arrayOf(permission), requestCode
        )
}