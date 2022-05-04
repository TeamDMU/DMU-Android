package com.dongyang.android.youdongknowme.ui.view.schedule

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentScheduleBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.standard.util.log
import com.dongyang.android.youdongknowme.ui.adapter.ScheduleAdapter
import org.threeten.bp.LocalDate


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

        // TODO :: 월 변경될 때마다 일정 리스트도 변경하기
        // TODO :: MinDate, MaxDate 설정 필요(리소스 감소)
        binding.scheduleCalendar.setOnMonthChangedListener { _, date ->
            val currentDate = date.year.toString() + "." + date.month.toString() // 2022.4
            adapter.submitList(viewModel.testCode2)
            log(currentDate)
        }

        // 연/월 방식으로 타이틀 처리
        binding.scheduleCalendar.setTitleFormatter { day ->
            val inputText: LocalDate = day.date
            val calendarHeaderElements = inputText.toString().split("-").toTypedArray()

            val year = calendarHeaderElements[0]
            val month = calendarHeaderElements[1]

            val calendarHeaderBuilder = StringBuilder()
            calendarHeaderBuilder.append(year)
                .append(getString(R.string.calendar_year))
                .append(" ")
                .append(month)
                .append(getString(R.string.calendar_month))
            calendarHeaderBuilder.toString()
        }
    }
}