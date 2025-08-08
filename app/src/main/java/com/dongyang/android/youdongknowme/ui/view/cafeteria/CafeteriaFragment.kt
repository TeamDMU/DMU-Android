package com.dongyang.android.youdongknowme.ui.view.cafeteria

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.window.layout.WindowMetricsCalculator
import com.kizitonwose.calendar.core.CalendarDay
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentCafeteriaBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.ui.adapter.CafeteriaAnotherAdapter
import com.dongyang.android.youdongknowme.ui.adapter.CafeteriaKoreanAdapter
import com.dongyang.android.youdongknowme.ui.view.util.EventObserver
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.view.DaySize
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.WeekDayBinder
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.DayOfWeek.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters

class CafeteriaFragment : BaseFragment<FragmentCafeteriaBinding, CafeteriaViewModel>(),
    CalendarInterface {

    override val layoutResourceId: Int = R.layout.fragment_cafeteria
    override val viewModel: CafeteriaViewModel by viewModel()

    private val koreanMenuAdapter by lazy { CafeteriaKoreanAdapter() }
    private val anotherMenuAdapter by lazy { CafeteriaAnotherAdapter() }

    override fun initStartView() {
        binding.vm = viewModel

        setupMenuRecyclerViews()
        setupCategoryToggleGroup()
        setupCalendar()
    }

    override fun initDataBinding() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) showLoading()
            else dismissLoading()
        }

        viewModel.errorState.observe(viewLifecycleOwner, EventObserver { resId ->
            showToast(getString(resId))
        })

        viewModel.koreanMenus.observe(viewLifecycleOwner) {
            koreanMenuAdapter.submitList(it)
        }

        viewModel.anotherMenus.observe(viewLifecycleOwner) {
            anotherMenuAdapter.submitList(it)
        }

        viewModel.selectedCategory.observe(viewLifecycleOwner) { selectedCategory ->
            updateCafeteriaState(selectedCategory)
        }

        viewModel.selectedDate.observe(viewLifecycleOwner) {
            viewModel.updateDaysMenu(it)
            viewModel.selectedCategory.value?.let { selectedCategory -> updateCafeteriaState(selectedCategory) }
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
            viewModel.updateDaysMenu(viewModel.selectedDate.value ?: nearestMonday)
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

    private fun setupMenuRecyclerViews() {
        binding.rvCafeteriaKoreanMenuList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CafeteriaFragment.koreanMenuAdapter
            setHasFixedSize(true)
        }

        binding.rvCafeteriaAnotherMenuList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CafeteriaFragment.anotherMenuAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupCategoryToggleGroup() {
        binding.tgCategory.check(binding.btnKorean.id)
        viewModel.setCategory(getString(R.string.cafeteria_korean))

        binding.tgCategory.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val category = when (checkedId) {
                    binding.btnKorean.id -> getString(R.string.cafeteria_korean)
                    binding.btnAnother.id -> getString(R.string.cafeteria_another)
                    else -> getString(R.string.cafeteria_korean)
                }
                viewModel.setCategory(category)
            }
        }
    }

    private fun setupCalendar() {
        binding.cvCafeteriaCalendar.apply {
            daySize = DaySize.Rectangle

            this.dayBinder = object : MonthDayBinder<CafeteriaContainer> {
                override fun create(view: View): CafeteriaContainer {
                    return CafeteriaContainer(view, this@apply, viewModel)
                }

                override fun bind(container: CafeteriaContainer, day: CalendarDay) {
                    val dayOfWeek = day.date.dayOfWeek
                    if (dayOfWeek == SATURDAY || dayOfWeek == SUNDAY) {
                        container.view.visibility = View.GONE
                    } else {
                        container.view.visibility = View.VISIBLE
                        container.bind(day)
                    }
                }
            }
        }
    }

    private fun updateCafeteriaState(selectedCategory: String) {
        val activeColor = ContextCompat.getColor(requireContext(), R.color.white)
        val inactiveColor = ContextCompat.getColor(requireContext(), R.color.gray200)
        val isWeekend =
            viewModel.selectedDate.value?.dayOfWeek == SATURDAY || viewModel.selectedDate.value?.dayOfWeek == SUNDAY

        binding.tvCafeteriaWeekend.isVisible = isWeekend

        binding.linearLayoutCafeteriaKorean.isVisible = selectedCategory == getString(R.string.cafeteria_korean) && !isWeekend
        binding.linearLayoutCafeteriaAnother.isVisible = selectedCategory == getString(R.string.cafeteria_another) && !isWeekend

        binding.btnKorean.setBackgroundColor(if (selectedCategory == getString(R.string.cafeteria_korean)) activeColor else inactiveColor)
        binding.btnAnother.setBackgroundColor(if (selectedCategory == getString(R.string.cafeteria_another)) activeColor else inactiveColor)
    }


    private fun findNearestMonday(currentDate: LocalDate): LocalDate {
        return when (currentDate.dayOfWeek) {
            SUNDAY -> {
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

    companion object {
        private const val DATE_CELL_COUNT = 5
        private const val DATE_CELL_HEIGHT_DP = 124f
    }
}
