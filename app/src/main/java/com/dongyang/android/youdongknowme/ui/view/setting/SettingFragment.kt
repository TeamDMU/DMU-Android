package com.dongyang.android.youdongknowme.ui.view.setting

import androidx.lifecycle.ViewModelProvider
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentSettingBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment

/* 설정 화면 */
class SettingFragment : BaseFragment<FragmentSettingBinding, SettingViewModel>() {
    companion object {
        fun newInstance() = SettingFragment()
    }

    override val layoutResourceId: Int = R.layout.fragment_setting
    override val viewModel: SettingViewModel by lazy {
        ViewModelProvider(this)[SettingViewModel::class.java]
    }

    override fun initStartView() {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}