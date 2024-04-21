package com.dongyang.android.youdongknowme.ui.view.setting

import android.app.Activity
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.os.Build
import android.view.View
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
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

    private lateinit var resultLauncherKeyword: ActivityResultLauncher<Intent>
    private lateinit var resultResultDepartment: ActivityResultLauncher<Intent>

    private var topics: List<String> = emptyList()
    private var department: String = ""

    override fun initStartView() {
        binding.tvSettingAppVersion.text = getAppVersion()
        setResultKeyword()
        setResultDepartment()
    }

    override fun initDataBinding() {

        viewModel.myDepartment.observe(viewLifecycleOwner) { myDepartment ->
            binding.tvSettingDepartment.text = myDepartment
            department = myDepartment
            viewModel.updateUserDepartment(department)
        }

        viewModel.myTopics.observe(viewLifecycleOwner) { myTopics ->
            topics = myTopics
            viewModel.updateUserTopic(topics)
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
            checkPermission(binding.switchSettingUniversityAlarm)
            if (compoundButton.isChecked) {
                if (topics.isNotEmpty()) {
                    viewModel.updateUserTopic(topics)
                }
            } else {
                viewModel.removeUserTopic()
            }
        }

        binding.switchSettingDepartmentAlarm.setOnCheckedChangeListener { compoundButton, _ ->
            checkPermission(binding.switchSettingDepartmentAlarm)
            if (compoundButton.isChecked) {
                if (department.isNotEmpty()) {
                    viewModel.updateUserDepartment(department)
                }
            } else {
                viewModel.removeUserDepartment()
            }
        }

        binding.btnSettingEditKeyword.setOnClickListener {
            val intent = Intent(requireActivity(), KeywordActivity::class.java)
            resultLauncherKeyword.launch(intent)
        }

        binding.btnSettingEditDepartment.setOnClickListener {
            val intent = Intent(requireActivity(), DepartActivity::class.java)
            resultResultDepartment.launch(intent)
        }

        binding.btnSettingAppHelp.setOnClickListener {
            val intent = Intent(
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

    private fun checkPermission(switch: SwitchCompat){
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                // 알림 권한 설정 미허용
                viewModel.setIsAccessDepartAlarm(false)
                viewModel.setIsAccessUniversityAlarm(false)
                binding.switchSettingUniversityAlarm.isChecked = false
                binding.switchSettingDepartmentAlarm.isChecked = false

                val dialog = PermissionDialog(getString(R.string.dialog_permission_title), getString(R.string.dialog_permission_content), requireContext().packageName)
                dialog.show(parentFragmentManager, "CustomDialog")
            } else {
                switch.isChecked = !switch.isChecked
            }
        } else {
            switch.isChecked = !switch.isChecked
        }
    }

    private fun getAppVersion(): String {
        val packageManager =
            requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
        return packageManager.versionName
    }

    private fun setResultKeyword() {
        resultLauncherKeyword =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    viewModel.getUserTopic().run { viewModel.updateUserTopic(topics) }
                    binding.switchSettingUniversityAlarm.isChecked = true
                }
            }
    }

    private fun setResultDepartment() {
        resultResultDepartment =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    viewModel.getUserDepartment()
                    binding.switchSettingDepartmentAlarm.isChecked = true
                }
            }
    }
}