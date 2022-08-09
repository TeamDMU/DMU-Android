package com.dongyang.android.youdongknowme.ui.view.cafeteria

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.util.Size
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentCafeteriaBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.ui.adapter.CafeteriaAdapter
import com.dongyang.android.youdongknowme.ui.adapter.CafeteriaEmployeeAdapter
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CafeteriaFragment : BaseFragment<FragmentCafeteriaBinding, CafeteriaViewModel>() {

    companion object {
        fun newInstance() = CafeteriaFragment()
    }
    private var selectedDate : LocalDate?=null
    //private var selectedDate = LocaleDate.now()

    private val dateFormatter = SimpleDateFormat("dd")
    private val dayFormatter = SimpleDateFormat("EEE")
    private val monthFormatter = SimpleDateFormat("MMM")

    //private val dateFormatter = DateTimeFormatter.ofPattern("dd")
    //private val dayFormatter = DateTimeFormatter.ofPattern("EEE")
    //private val monthFormatter = DateTimeFormatter.ofPattern("MMM")

    override val layoutResourceId: Int = R.layout.fragment_cafeteria
    override val viewModel: CafeteriaViewModel by viewModel()

    private lateinit var cafeteriaAdapter: CafeteriaAdapter
    private lateinit var cafeteriaEmployeeAdapter: CafeteriaEmployeeAdapter

    private var menuList = arrayListOf("시금치된장국", "오이냉국","시금치된장국", "오이냉국","시금치된장국", "오이냉국","시금치된장국", "오이냉국")
    private var employee_menuList = arrayListOf("짜장덮밥", "닭볶음탕","무생채","알감자조림","도시락김","치킨마요덮밥","매운닭갈비","간장")

    override fun initStartView() {

        val dm = DisplayMetrics()
        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)
        binding.exSevenCalendar.apply{
            val dayWidth = dm.widthPixels / 5
            val dayHeight = (dayWidth * 1.25).toInt()
            daySize = com.kizitonwose.calendarview.utils.Size(dayWidth,dayHeight)
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            val bind = FragmentCafeteriaBinding.bind(view)
            lateinit var day: CalendarDay

            init {
                view.setOnClickListener {
                    val firstDay = binding.exSevenCalendar.findFirstVisibleDay()
                    val lastDay = binding.exSevenCalendar.findLastVisibleDay()
                    if (firstDay == day) {
                        binding.exSevenCalendar.smoothScrollToDate(day.date)
                    } else if (lastDay == day) {

                        binding.exSevenCalendar.smoothScrollToDate(day.date.minusDays(4))
                    }

                    if (selectedDate != day.date) {
                        val oldDate = selectedDate
                        selectedDate = day.date
                        binding.exSevenCalendar.notifyDateChanged(day.date)
                        oldDate?.let { binding.exSevenCalendar.notifyDateChanged(it) }
                    }

                    Log.w("TAG :: ", selectedDate.toString())
                    //binding.exSevenText.text = selectedDate.toString()
                }
            }

            fun bind(day: CalendarDay) {
                this.day = day
                bind.DateText.text = dateFormatter.format(day.date)
                bind.DayText.text = dayFormatter.format(day.date)
                bind.MonthText.text = monthFormatter.format(day.date)

                bind.DateText.setTextColor(view.context.getColorCompat(if (day.date == selectedDate) R.color.black else R.color.white))
                //bind.exSevenSelectedView.isVisible = day.date == selectedDate
            }
        }

        binding.exSevenCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) = container.bind(day)
        }

        cafeteriaAdapter = CafeteriaAdapter()
        cafeteriaEmployeeAdapter = CafeteriaEmployeeAdapter()


        binding.stuMenuList.apply {
            this.adapter = this@CafeteriaFragment.cafeteriaAdapter
            this.layoutManager = LinearLayoutManager(requireActivity())
            this.setHasFixedSize(true)
            this.addItemDecoration(DividerItemDecoration(requireActivity(), 1))
        }

        binding.employeeMenuList.apply{
            this.adapter = this@CafeteriaFragment.cafeteriaEmployeeAdapter
            this.layoutManager = LinearLayoutManager(requireActivity())
            this.setHasFixedSize(true)
            this.addItemDecoration(DividerItemDecoration(requireActivity(),1))
        }
    }

    override fun initDataBinding() {
        cafeteriaAdapter.submitList(menuList)
        cafeteriaEmployeeAdapter.submitList(employee_menuList)
    }

    override fun initAfterBinding() {
        //현재달
        //val currentMonth = YearMonth.now()
        //최소 달,최대 달 입력하는 부분
        //binding.exSevenCalendar.setup(currentMonth.minusMonths(3), currentMonth.plusMonths(12), DayOfWeek.values().random())
        //초기 날짜 세팅
        //binding.exSevenCalendar.scrollToDate(LocaleDate)
    }
}


