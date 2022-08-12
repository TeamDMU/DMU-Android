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

    override fun initStartView() {
        binding.vm = viewModel

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

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) showLoading()
            else dismissLoading()
        }

        viewModel.stuMenuList.observe(viewLifecycleOwner) {
            cafeteriaAdapter.submitList(it)
        }

        viewModel.eduMenuList.observe(viewLifecycleOwner) {
            cafeteriaEmployeeAdapter.submitList(it)
        }
    }

    override fun initAfterBinding() {
        binding.cafeteriaCalendar.setup(
            YearMonth.now().minusMonths(1),
            YearMonth.now().plusMonths(1),
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
