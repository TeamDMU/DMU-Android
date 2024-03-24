package com.dongyang.android.youdongknowme.ui.view.cafeteria

import android.view.View
import androidx.window.layout.WindowMetricsCalculator
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentCafeteriaBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.ui.adapter.CafeteriaAdapter
import com.dongyang.android.youdongknowme.ui.view.util.EventObserver
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.utils.Size
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth


class CafeteriaFragment : BaseFragment<FragmentCafeteriaBinding, CafeteriaViewModel>(),
    CalendarInterface {

    override val layoutResourceId: Int = R.layout.fragment_cafeteria
    override val viewModel: CafeteriaViewModel by viewModel()

    private lateinit var koreanMenuAdapter: CafeteriaAdapter
    private lateinit var anotherMenuAdapter: CafeteriaAdapter

    override fun initStartView() {
        binding.vm = viewModel

        koreanMenuAdapter = CafeteriaAdapter()
        anotherMenuAdapter = CafeteriaAdapter()

        binding.rvCafeteriaMenuList.apply {
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexDirection = FlexDirection.ROW
            this.adapter = this@CafeteriaFragment.koreanMenuAdapter
            this.layoutManager = layoutManager
            this.setHasFixedSize(true)
        }

        binding.rvCafeteriaAnotherMenuList.apply {
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexDirection = FlexDirection.ROW
            this.adapter = this@CafeteriaFragment.anotherMenuAdapter
            this.layoutManager = layoutManager
            this.setHasFixedSize(true)
        }

        val wmc =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(requireActivity())

        binding.cvCafeteriaCalendar.apply {
            val dayWidth = wmc.bounds.width() / 5
            val dayHeight: Int = (dayWidth * 1.25).toInt()

            daySize = Size(dayWidth, dayHeight)
        }

        binding.cvCafeteriaCalendar.dayBinder = object : DayBinder<CafeteriaContainer> {
            override fun create(view: View): CafeteriaContainer =
                CafeteriaContainer(view, binding.cvCafeteriaCalendar, viewModel)

            override fun bind(container: CafeteriaContainer, day: CalendarDay) = container.bind(day)
        }
    }

    override fun initDataBinding() {

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) showLoading()
            else dismissLoading()
        }

        viewModel.errorState.observe(viewLifecycleOwner, EventObserver { resId ->
            showToast(getString(resId))
        })

        viewModel.menus.observe(viewLifecycleOwner) {
            koreanMenuAdapter.submitList(it)
            // 일품 메뉴 : 일품 메뉴는 리스트로 제작하여 등록
            anotherMenuAdapter.submitList(listOf(getString(R.string.cafeteria_no_menu)))
        }

    }

    override fun initAfterBinding() {
        val nearestMonday = findNearestMonday(LocalDate.now())

        binding.cvCafeteriaCalendar.setup(
            YearMonth.from(nearestMonday),
            YearMonth.from(nearestMonday.plusDays(4)),
            DayOfWeek.MONDAY
        )
        binding.cvCafeteriaCalendar.scrollToDate(nearestMonday)

        binding.cafeteriaErrorContainer.refresh.setOnClickListener {
            viewModel.fetchCafeteria()
        }
    }

    private fun findNearestMonday(currentDate: LocalDate): LocalDate {
        var date = currentDate

        if (date.dayOfWeek == DayOfWeek.SATURDAY || date.dayOfWeek == DayOfWeek.SUNDAY) {
            date = date.plusDays((8 - date.dayOfWeek.value).toLong())
        }

        while (date.dayOfWeek != DayOfWeek.MONDAY) {
            date = date.minusDays(1)
        }

        return date
    }

    override fun onPause() {
        super.onPause()
        notifyDateChanged(
            viewModel,
            binding.cvCafeteriaCalendar,
            viewModel.selectedDate.value,
            LocalDate.now()
        )
    }
}
