package com.dongyang.android.youdongknowme.ui.view.setting

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var topics: List<String> = emptyList()

    override fun initStartView() {
        binding.tvSettingAppVersion.text = getAppVersion()
        setResultKeyword()
    }

    override fun initDataBinding() {

        viewModel.myDepartment.observe(viewLifecycleOwner) { myDepartment ->
            binding.tvSettingDepartment.text = myDepartment
        }

        viewModel.myTopics.observe(viewLifecycleOwner) { myTopics ->
            topics = topics
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
                viewModel.updateUserTopic(topics)
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
            resultLauncher.launch(intent)
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

    private fun setResultKeyword() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    viewModel.getUserTopic()
                    viewModel.updateUserTopic(topics)
                }
            }
    }
}