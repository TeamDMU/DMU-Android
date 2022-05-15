package com.dongyang.android.youdongknowme.ui.view.setting

import android.content.Context
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentSettingBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment

/* 설정 화면 */
class SettingFragment : BaseFragment<FragmentSettingBinding, SettingViewModel>() {
    companion object {
        fun newInstance() = SettingFragment()
    }

    override val layoutResourceId: Int = R.layout.fragment_setting
    override val viewModel: SettingViewModel by viewModel()

    //현재 app version 불러오기 (1.0.0)
    private fun getAppVersion(context: Context):String? {
        var versionName = ""
        try {
            val packageManager = context.packageManager.getPackageInfo(context.packageName,0)
            versionName = packageManager.versionName
        } catch (e:Exception){

        }
        return versionName
    }

    override fun initStartView() {
        binding!!.settingVersion.text =  "앱버전 " + getAppVersion(requireContext())

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}