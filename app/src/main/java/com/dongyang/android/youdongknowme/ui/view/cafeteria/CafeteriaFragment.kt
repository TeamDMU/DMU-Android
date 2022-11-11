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

    private lateinit var stuKoreanMenuAdapter: CafeteriaAdapter
    private lateinit var stuAnotherMenuAdapter: CafeteriaAdapter
    private lateinit var eduKoreanMenuAdapter: CafeteriaAdapter
    private lateinit var eduAnotherMenuAdapter: CafeteriaAdapter

    override fun initStartView() {
        binding.vm = viewModel

        stuKoreanMenuAdapter = CafeteriaAdapter()
        stuAnotherMenuAdapter = CafeteriaAdapter()
        eduKoreanMenuAdapter = CafeteriaAdapter()
        eduAnotherMenuAdapter = CafeteriaAdapter()

        binding.cafeteriaStuKoreanMenuList.apply {
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexDirection = FlexDirection.ROW
            this.adapter = this@CafeteriaFragment.stuKoreanMenuAdapter
            this.layoutManager = layoutManager
            this.setHasFixedSize(true)
        }

        binding.cafeteriaStuAnotherMenuList.apply {
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexDirection = FlexDirection.ROW
            this.adapter = this@CafeteriaFragment.stuAnotherMenuAdapter
            this.layoutManager = layoutManager
            this.setHasFixedSize(true)
        }

        binding.cafeteriaEduKoreanMenuList.apply {
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexDirection = FlexDirection.ROW
            this.adapter = this@CafeteriaFragment.eduKoreanMenuAdapter
            this.layoutManager = layoutManager
            this.setHasFixedSize(true)
        }

        binding.cafeteriaEduAnotherMenuList.apply {
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexDirection = FlexDirection.ROW
            this.adapter = this@CafeteriaFragment.eduAnotherMenuAdapter
            this.layoutManager = layoutManager
            this.setHasFixedSize(true)
        }

        val wmc =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(requireActivity())

        binding.cafeteriaCalendar.apply {
            val dayWidth = wmc.bounds.width() / 5
            val dayHeight: Int = (dayWidth * 1.25).toInt()

            daySize = Size(dayWidth, dayHeight)
        }

        binding.cafeteriaCalendar.dayBinder = object : DayBinder<CafeteriaContainer> {
            override fun create(view: View): CafeteriaContainer =
                CafeteriaContainer(view, binding.cafeteriaCalendar, viewModel)

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

        viewModel.stuMenus.observe(viewLifecycleOwner) {
            stuKoreanMenuAdapter.submitList(it.first)
            stuAnotherMenuAdapter.submitList(it.second)
        }

        viewModel.eduMenus.observe(viewLifecycleOwner) {
            eduKoreanMenuAdapter.submitList(it.first)
            eduAnotherMenuAdapter.submitList(it.second)
        }
    }

    override fun initAfterBinding() {
        binding.cafeteriaCalendar.setup(
            YearMonth.now().minusMonths(2),
            YearMonth.now().plusMonths(1),
            DayOfWeek.values().random()
        )

        binding.cafeteriaCalendar.scrollToDate(LocalDate.now().minusDays(2))

        binding.cafeteriaErrorContainer.refresh.setOnClickListener {
            viewModel.fetchCafeteria()
        }
    }

    override fun onPause() {
        super.onPause()
        notifyDateChanged(
            viewModel,
            binding.cafeteriaCalendar,
            viewModel.selectedDate.value,
            LocalDate.now()
        )
    }
}
