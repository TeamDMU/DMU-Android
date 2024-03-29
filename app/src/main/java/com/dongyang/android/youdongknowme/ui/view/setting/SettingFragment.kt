package com.dongyang.android.youdongknowme.ui.view.setting

import android.content.Intent
import android.net.Uri
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

    override fun initDataBinding() {

        viewModel.myDepartment.observe(viewLifecycleOwner) { department ->
            binding.tvSettingDepartment.text = department
        }

        viewModel.isAccessUniversityAlarm.observe(viewLifecycleOwner) { isChecked ->
            binding.switchSettingUniversityAlarm.isChecked = isChecked
        }

        viewModel.isAccessDepartAlarm.observe(viewLifecycleOwner) { isChecked ->
            binding.switchSettingDepartmentAlarm.isChecked = isChecked
        }
    }

    override fun initAfterBinding() {

        viewModel.checkAccessAlarm()
        viewModel.getUserDepartment()
        viewModel.getUserTopic()

        binding.switchSettingUniversityAlarm.setOnCheckedChangeListener { compoundButton, _ ->
            if (compoundButton.isChecked) {
                viewModel.updateUserTopic()
            } else {
                viewModel.removeUserTopic()
            }
        }

        binding.switchSettingDepartmentAlarm.setOnCheckedChangeListener { compoundButton, _ ->
            if (compoundButton.isChecked) {
                viewModel.updateUserDepartment()
            } else {
                viewModel.removeUserDepartment()
            }
        }

        binding.btnSettingEditKeyword.setOnClickListener {
            val intent = Intent(requireActivity(), KeywordActivity::class.java)
            startActivity(intent)
        }

        binding.btnSettingEditDepartment.setOnClickListener {
            val intent = Intent(requireActivity(), DepartActivity::class.java)
            startActivity(intent)
        }

        binding.btnSettingAppHelp.setOnClickListener {
            val intent =
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSeRTKalenelmffTbCZeK4mqmQg0palobghkXSoie1FlmV22ZQ/viewform")
                )
            startActivity(intent)
        }

        binding.btnSettingAppPersonalPolicy.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://sites.google.com/view/dmforu-privacy-policy/%ED%99%88")
            )
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