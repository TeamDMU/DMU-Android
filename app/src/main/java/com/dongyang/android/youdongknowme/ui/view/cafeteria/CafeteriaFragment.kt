package com.dongyang.android.youdongknowme.ui.view.cafeteria

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentCafeteriaBinding
import com.dongyang.android.youdongknowme.databinding.ItemCalendarDayBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.ui.adapter.CafeteriaAdapter
import com.dongyang.android.youdongknowme.ui.adapter.CafeteriaEmployeeAdapter
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.Size
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CafeteriaFragment : BaseFragment<FragmentCafeteriaBinding, CafeteriaViewModel>() {

    companion object {
        fun newInstance() = CafeteriaFragment()
    }

    override val layoutResourceId: Int = R.layout.fragment_cafeteria
    override val viewModel: CafeteriaViewModel by viewModel()

    private var selectedDate = LocalDate.now()

    private val dateFormatter = DateTimeFormatter.ofPattern("dd")
    private val dayFormatter = DateTimeFormatter.ofPattern("EEE")
    private val monthFormatter = DateTimeFormatter.ofPattern("MMM")

    private lateinit var cafeteriaAdapter: CafeteriaAdapter
    private lateinit var cafeteriaEmployeeAdapter: CafeteriaEmployeeAdapter

    private var menuList =
        arrayListOf("시금치된장국", "오이냉국", "시금치된장국", "오이냉국", "시금치된장국", "오이냉국", "시금치된장국", "오이냉국")
    private var employeeMenuList =
        arrayListOf("짜장덮밥", "닭볶음탕", "무생채", "알감자조림", "도시락김", "치킨마요덮밥", "매운닭갈비", "간장")

    override fun initStartView() {

        cafeteriaAdapter = CafeteriaAdapter()
        cafeteriaEmployeeAdapter = CafeteriaEmployeeAdapter()

        binding.stuMenuList.apply {
            this.adapter = this@CafeteriaFragment.cafeteriaAdapter
            this.layoutManager = LinearLayoutManager(requireActivity())
            this.setHasFixedSize(true)
        }

        binding.employeeMenuList.apply {
            this.adapter = this@CafeteriaFragment.cafeteriaEmployeeAdapter
            this.layoutManager = LinearLayoutManager(requireActivity())
            this.setHasFixedSize(true)
        }

        val dm = DisplayMetrics()
        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager

        // TODO : deprecated -> 수정 필요
        wm.defaultDisplay.getMetrics(dm)
        binding.cafeteriaCalendar.apply {
            val dayWidth = dm.widthPixels / 5
            val dayHeight = (dayWidth * 1.25).toInt()
            daySize = Size(dayWidth, dayHeight)
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            val bind = ItemCalendarDayBinding.bind(view)
            lateinit var day: CalendarDay

            init {
                view.setOnClickListener {
                    if (selectedDate != day.date) {
                        val oldDate = selectedDate
                        selectedDate = day.date
                        binding.cafeteriaCalendar.notifyDateChanged(day.date)
                        oldDate?.let { binding.cafeteriaCalendar.notifyDateChanged(it) }
                    }
                }
            }

            fun bind(day: CalendarDay) {
                this.day = day
                bind.itemCalendarDate.text = dateFormatter.format(day.date)
                bind.itemCalendarDay.text = dayFormatter.format(day.date)
                bind.itemCalendarMonth.text = monthFormatter.format(day.date)

                bind.itemCalendarDate.setTextColor(
                    ContextCompat.getColor(
                        view.context,
                        if (day.date == selectedDate) R.color.main else R.color.black
                    )
                )
            }
        }

        binding.cafeteriaCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) = container.bind(day)
        }
    }

    override fun initDataBinding() {
        cafeteriaAdapter.submitList(menuList)
        cafeteriaEmployeeAdapter.submitList(employeeMenuList)
    }

    override fun initAfterBinding() {
        // 현재달
        val currentMonth = YearMonth.now()
        // 최소 달,최대 달 입력하는 부분
        binding.cafeteriaCalendar.setup(
            currentMonth.minusMonths(3),
            currentMonth.plusMonths(12),
            DayOfWeek.values().random()
        )
        // 초기 날짜 세팅
        binding.cafeteriaCalendar.scrollToDate(LocalDate.now().minusDays(2))
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            binding.cafeteriaCalendar.stopScroll()
            binding.cafeteriaCalendar.scrollToDate(LocalDate.now().minusDays(2))
            selectedDate = LocalDate.now()
        }
    }
}


