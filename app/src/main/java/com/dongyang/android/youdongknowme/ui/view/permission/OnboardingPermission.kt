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
        setSpan(binding.tvAlarmTitleMain,startIdx = 0, endIdx = 9)
    }

    override fun initDataBinding() = Unit

    @RequiresApi(Build.VERSION_CODES.P)
    override fun initAfterBinding() {
        binding.btnAlarmComplete.setOnClickListener {
            val intent = Intent(this@OnboardingPermission, MainActivity::class.java)
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

    // 텍스트 부분 색상 설정
    private fun setSpan(spanTextView: TextView, startIdx: Int, endIdx: Int){
        SpannableStringBuilder(spanTextView.text).apply {
            setSpan(
                ForegroundColorSpan(getColor(R.color.main)),
                startIdx,
                endIdx,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spanTextView.text = this
        }
    }

}