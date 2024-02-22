package com.dongyang.android.youdongknowme.ui.view.permission

import android.content.Intent
import android.os.Build
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityOnboardingPermissionBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.ui.view.main.MainActivity
import com.dongyang.android.youdongknowme.ui.view.setting.SettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingPermission : BaseActivity<ActivityOnboardingPermissionBinding, SettingViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_onboarding_permission
    override val viewModel: SettingViewModel by viewModel()

    override fun initStartView() {
        setSpanText(binding.tvPermissionTitleMain,startIdx = 0, endIdx = 9)
    }

    override fun initDataBinding() = Unit

    @RequiresApi(Build.VERSION_CODES.P)
    override fun initAfterBinding() {
        binding.btnPermissionComplete.setOnClickListener {
            val intent = Intent(this@OnboardingPermission, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.switchPermission.setOnCheckedChangeListener { compoundButton, _ ->
            if (compoundButton.isChecked) {
                viewModel.setIsAccessDepartAlarm(true)
                binding.mvSwitchPermission.outlineAmbientShadowColor = getColor(R.color.blue300)
                binding.mvSwitchPermission.outlineSpotShadowColor = getColor(R.color.blue300)
                binding.switchPermission.setTextColor(getColor(R.color.blue300))
            } else {
                viewModel.setIsAccessDepartAlarm(false)
                binding.mvSwitchPermission.outlineAmbientShadowColor = getColor(R.color.gray300)
                binding.mvSwitchPermission.outlineSpotShadowColor = getColor(R.color.gray300)
                binding.switchPermission.setTextColor(getColor(R.color.gray300))
            }
        }
    }

}