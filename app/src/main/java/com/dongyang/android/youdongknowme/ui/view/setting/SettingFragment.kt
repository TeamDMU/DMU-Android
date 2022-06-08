package com.dongyang.android.youdongknowme.ui.view.setting

import android.content.Context
import android.content.Intent
import android.widget.Toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentSettingBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.ui.view.depart.DepartActivity
import com.dongyang.android.youdongknowme.ui.view.keyword.KeywordActivity
import com.dongyang.android.youdongknowme.ui.view.main.MainActivity

/* 설정 화면 */
class SettingFragment : BaseFragment<FragmentSettingBinding, SettingViewModel>() {
    companion object {
        fun newInstance() = SettingFragment()
    }

    override val layoutResourceId: Int = R.layout.fragment_setting
    override val viewModel: SettingViewModel by viewModel()


    override fun initStartView() {
        binding.settingVersion.text = getAppVersion()
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        // 이메일 연동 코드
        binding.settingAsk.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "plain/text"
            val address = arrayOf("jiwon0705@m365.dongyang.ac.kr") // 이메일 주소는 배열로 묶어주기
            intent.putExtra(Intent.EXTRA_EMAIL, address)
            startActivity(intent)
        }

        // 학과 설정 눌렀을 때
        binding.settingDepartmentChoice.setOnClickListener {
            val intent = Intent(requireActivity(), DepartActivity::class.java)
            startActivity(intent)
        }

        binding.settingKeyword.setOnClickListener {
            val intent = Intent(requireActivity(), KeywordActivity::class.java)
            startActivity(intent)
        }
    }

    // 현재 app version 불러오기 (1.0.0)
    private fun getAppVersion(): String {
        val packageManager =
            requireContext().packageManager.getPackageInfo(requireContext().packageName,0)
        return packageManager.versionName
    }

}