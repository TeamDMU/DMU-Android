package com.dongyang.android.youdongknowme.ui.view.schedule

import androidx.lifecycle.ViewModelProvider
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentScheduleBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


/* 학사 일정 화면 */
class ScheduleFragment : BaseFragment<FragmentScheduleBinding, ScheduleViewModel>() {
    companion object {
        fun newInstance() = ScheduleFragment()
    }

    override val layoutResourceId: Int = R.layout.fragment_schedule
    override val viewModel: ScheduleViewModel by viewModel()

    override fun initStartView() {
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}