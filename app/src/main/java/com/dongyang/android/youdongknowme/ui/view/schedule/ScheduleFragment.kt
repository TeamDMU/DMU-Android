package com.dongyang.android.youdongknowme.ui.view.schedule

import androidx.lifecycle.ViewModelProvider
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentScheduleBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.standard.util.log


/* 학사 일정 화면 */
class ScheduleFragment : BaseFragment<FragmentScheduleBinding, ScheduleViewModel>() {
    companion object {
        fun newInstance() = ScheduleFragment()
    }

    override val layoutResourceId: Int = R.layout.fragment_schedule
    override val viewModel: ScheduleViewModel by lazy {
        ViewModelProvider(this)[ScheduleViewModel::class.java]
    }

    override fun initStartView() {
        log(binding.scheduleCalendar.currentDate.date.toString()) // 달력 초기 데이터 (2022-04-01)
    }

    override fun initDataBinding() {



    }

    override fun initAfterBinding() {

        // TODO :: 월 변경될 때마다 학사 일정 리스트도 변경하기
        binding.scheduleCalendar.setOnMonthChangedListener { _, date ->
            val currentDate = date.year.toString() + "." + date.month.toString() // 2022.4
            log(currentDate)
        }

    }
}