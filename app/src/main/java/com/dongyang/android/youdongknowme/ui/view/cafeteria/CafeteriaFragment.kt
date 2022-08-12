package com.dongyang.android.youdongknowme.ui.view.cafeteria

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentCafeteriaBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.ui.adapter.CafeteriaAdapter
import com.dongyang.android.youdongknowme.ui.adapter.CafeteriaEmployeeAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.utils.Size
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

class CafeteriaFragment : BaseFragment<FragmentCafeteriaBinding, CafeteriaViewModel>(), CalendarInterface {

    companion object {
        fun newInstance() = CafeteriaFragment()
    }

    override val layoutResourceId: Int = R.layout.fragment_cafeteria
    override val viewModel: CafeteriaViewModel by viewModel()

    private lateinit var cafeteriaAdapter: CafeteriaAdapter
    private lateinit var cafeteriaEmployeeAdapter: CafeteriaEmployeeAdapter

    private var menuList = arrayListOf("잡곡밥", "소고기육개장", "계란말이", "미트볼떡조림", "치커리유자청무침", "석박지")
    private var employeeMenuList = arrayListOf("짜장덮밥", "닭볶음탕", "무생채", "알감자조림", "도시락김", "치킨마요덮밥", "매운닭갈비", "간장")

    override fun initStartView() {

        cafeteriaAdapter = CafeteriaAdapter()
        cafeteriaEmployeeAdapter = CafeteriaEmployeeAdapter()

        binding.stuMenuList.apply {
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexDirection = FlexDirection.ROW
            this.adapter = this@CafeteriaFragment.cafeteriaAdapter
            this.layoutManager = layoutManager
            this.setHasFixedSize(true)
        }

        binding.employeeMenuList.apply {
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexDirection = FlexDirection.ROW
            this.adapter = this@CafeteriaFragment.cafeteriaEmployeeAdapter
            this.layoutManager = layoutManager
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

        binding.cafeteriaCalendar.dayBinder = object : DayBinder<CafeteriaContainer> {
            override fun create(view: View): CafeteriaContainer = CafeteriaContainer(view, binding.cafeteriaCalendar, viewModel)
            override fun bind(container: CafeteriaContainer, day: CalendarDay) = container.bind(day)
        }
    }

    override fun initDataBinding() {
        cafeteriaAdapter.submitList(menuList)
        cafeteriaEmployeeAdapter.submitList(employeeMenuList)
    }

    override fun initAfterBinding() {
        val currentMonth = YearMonth.now()
        binding.cafeteriaCalendar.setup(
            currentMonth.minusMonths(1),
            currentMonth.plusMonths(1),
            DayOfWeek.values().random()
        )
        binding.cafeteriaCalendar.scrollToDate(LocalDate.now().minusDays(2))
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            binding.cafeteriaCalendar.stopScroll()
            binding.cafeteriaCalendar.smoothScrollToDate(LocalDate.now().minusDays(2))
            notifyDateChanged(viewModel, binding.cafeteriaCalendar, viewModel.selectedDate.value, LocalDate.now())
        }
    }
}
