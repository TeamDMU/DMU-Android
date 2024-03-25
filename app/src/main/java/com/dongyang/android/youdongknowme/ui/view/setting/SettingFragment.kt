package com.dongyang.android.youdongknowme.ui.view.setting

import android.content.Intent
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentSettingBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.ui.view.depart.DepartActivity
import com.dongyang.android.youdongknowme.ui.view.keyword.KeywordActivity
import com.dongyang.android.youdongknowme.ui.view.license.LicenseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/* 설정 화면 */
class SettingFragment : BaseFragment<FragmentSettingBinding, SettingViewModel>() {

    override val layoutResourceId: Int = R.layout.fragment_setting
    override val viewModel: SettingViewModel by viewModel()

    override fun initStartView() {
        binding.tvSettingAppVersion.text = getAppVersion()
    }

    override fun initDataBinding() = Unit

    override fun initAfterBinding() {
        viewModel.checkAccessAlarm()
        viewModel.getUserDepartment()

        binding.switchSettingUniversityAlarm.setOnCheckedChangeListener { compoundButton, _ ->
            if (compoundButton.isChecked) {
                viewModel.setIsAccessSchoolAlarm(true)
            } else {
                viewModel.setIsAccessSchoolAlarm(false)
            }
        }

        binding.switchSettingDepartmentAlarm.setOnCheckedChangeListener { compoundButton, _ ->
            if (compoundButton.isChecked) {
                viewModel.setIsAccessDepartAlarm(true)
            } else {
                viewModel.setIsAccessDepartAlarm(false)
            }
        }

        // 이메일 연동
        binding.btnSettingAppHelp.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "plain/text"
            val address = arrayOf("https://forms.gle/8ZKfV96qyisLu1pcA")
            intent.putExtra(Intent.EXTRA_EMAIL, address)
            startActivity(intent)
        }

        binding.btnSettingEditDepartment.setOnClickListener {
            val intent = Intent(requireActivity(), DepartActivity::class.java)
            startActivity(intent)
        }

        binding.btnSettingEditKeyword.setOnClickListener {
            val intent = Intent(requireActivity(), KeywordActivity::class.java)
            startActivity(intent)
        }

        binding.btnSettingAppOpensource.setOnClickListener {
            val intent = Intent(requireActivity(), LicenseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getAppVersion(): String {
        val packageManager =
            requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
        return packageManager.versionName
    }
}