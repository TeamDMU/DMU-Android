package com.dongyang.android.youdongknowme.ui.view.schedule

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentScheduleBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.standard.util.log
import com.dongyang.android.youdongknowme.ui.adapter.ScheduleAdapter


/* 학사 일정 화면 */
class ScheduleFragment : BaseFragment<FragmentScheduleBinding, ScheduleViewModel>() {
    companion object {
        fun newInstance() = ScheduleFragment()
    }

    override val layoutResourceId: Int = R.layout.fragment_schedule
    override val viewModel: ScheduleViewModel by lazy {
        ViewModelProvider(this)[ScheduleViewModel::class.java]
    }

    private lateinit var adapter : ScheduleAdapter

    override fun initStartView() {
        log(binding.scheduleCalendar.currentDate.date.toString()) // 달력 초기 데이터 (2022-04-01)
        adapter = ScheduleAdapter()
        binding.scheduleRvList.apply {
            this.adapter = this@ScheduleFragment.adapter
            this.layoutManager = LinearLayoutManager(requireActivity())
            this.setHasFixedSize(true)
            this.addItemDecoration(DividerItemDecoration(requireActivity(),1))
        }
    }

    override fun initDataBinding() {
        adapter.submitList(viewModel.testCode)
    }

    override fun initAfterBinding() {

        // TODO :: 월 변경될 때마다 학사 일정 리스트도 변경하기
        binding.scheduleCalendar.setOnMonthChangedListener { _, date ->
            val currentDate = date.year.toString() + "." + date.month.toString() // 2022.4
            adapter.submitList(viewModel.testCode2)
            log(currentDate)
        }

    }
}