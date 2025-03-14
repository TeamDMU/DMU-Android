package com.dongyang.android.youdongknowme.ui.view.cafeteria

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.window.layout.WindowMetricsCalculator
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentCafeteriaBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.ui.adapter.CafeteriaAnotherAdapter
import com.dongyang.android.youdongknowme.ui.adapter.CafeteriaKoreanAdapter
import com.dongyang.android.youdongknowme.ui.view.util.EventObserver
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.utils.Size
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.DayOfWeek.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters

class CafeteriaFragment : BaseFragment<FragmentCafeteriaBinding, CafeteriaViewModel>(),
    CalendarInterface {

    override val layoutResourceId: Int = R.layout.fragment_cafeteria
    override val viewModel: CafeteriaViewModel by viewModel()

    private lateinit var koreanMenuAdapter: CafeteriaKoreanAdapter
    private lateinit var anotherMenuAdapter: CafeteriaAnotherAdapter

    override fun initStartView() {
        binding.vm = viewModel

        koreanMenuAdapter = CafeteriaKoreanAdapter()
        anotherMenuAdapter = CafeteriaAnotherAdapter()

        binding.rvCafeteriaMenuList.apply {
            val layoutManager = LinearLayoutManager(context)
            this.adapter = this@CafeteriaFragment.koreanMenuAdapter
            this.layoutManager = layoutManager
            this.setHasFixedSize(true)
        }

        binding.rvCafeteriaAnotherMenuList.apply {
            val layoutManager = LinearLayoutManager(context)
            this.adapter = this@CafeteriaFragment.anotherMenuAdapter
            this.layoutManager = layoutManager
            this.setHasFixedSize(true)
        }

        val wmc =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(requireActivity())

        binding.cvCafeteriaCalendar.apply {
            val dayWidth = wmc.bounds.width() / 5
            val dayHeight: Int = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                124f,
                resources.displayMetrics
            ).toInt()

            daySize = Size(dayWidth, dayHeight)
        }

        binding.cvCafeteriaCalendar.dayBinder = object : DayBinder<CafeteriaContainer> {
            override fun create(view: View): CafeteriaContainer =
                CafeteriaContainer(view, binding.cvCafeteriaCalendar, viewModel)

            override fun bind(container: CafeteriaContainer, day: CalendarDay) = container.bind(day)
        }

        viewModel.updateDaysMenu(findNearestMonday(LocalDate.now()))

        binding.tgCategory.check(binding.btnKorean.id)
        viewModel.setCategory("한식")
        updateButtonColors("한식")

        binding.tgCategory.addOnButtonCheckedListener { _, checkedId, isChecked ->
            val category = when (checkedId) {
                binding.btnKorean.id -> "한식"
                binding.btnAnother.id -> "일품"
                else -> ""
            }
            if (isChecked) {
                viewModel.setCategory(category)
            }
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

        viewModel.koreaMenus.observe(viewLifecycleOwner) {
            koreanMenuAdapter.submitList(it)
        }

        viewModel.daysMenus.observe(viewLifecycleOwner) {
            anotherMenuAdapter.submitList(it)
        }

        viewModel.selectedCategory.observe(viewLifecycleOwner) { selectedCategory ->
            updateButtonColors(selectedCategory)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initAfterBinding() {
        val nearestMonday = findNearestMonday(LocalDate.now())

        binding.cvCafeteriaCalendar.setup(
            YearMonth.from(nearestMonday),
            YearMonth.from(nearestMonday.plusDays(4)),
            MONDAY
        )

        binding.cvCafeteriaCalendar.scrollToDate(nearestMonday)

        binding.cafeteriaErrorContainer.refresh.setOnClickListener {
            viewModel.fetchCafeteria()
            viewModel.updateDaysMenu(findNearestMonday(LocalDate.now()))
        }

        binding.cvCafeteriaCalendar.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    true
                }

                else -> false
            }
        }
    }

    private fun findNearestMonday(currentDate: LocalDate): LocalDate {
        return when (currentDate.dayOfWeek) {
            SATURDAY, SUNDAY -> {
                currentDate.with(TemporalAdjusters.next(MONDAY))
            }

            MONDAY -> {
                currentDate
            }

            else -> {
                currentDate.with(TemporalAdjusters.previous(MONDAY))
            }
        }
    }

    private fun updateButtonColors(selectedCategory: String) {
        binding.mvCafeteriaMenu.isVisible = selectedCategory == "한식"
        binding.mvCafeteriaAnother.isVisible = selectedCategory == "일품"

        binding.btnKorean.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                if (selectedCategory == "한식") R.color.white else R.color.gray200
            )
        )
        binding.btnAnother.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                if (selectedCategory == "일품") R.color.white else R.color.gray200
            )
        )
    }
}
