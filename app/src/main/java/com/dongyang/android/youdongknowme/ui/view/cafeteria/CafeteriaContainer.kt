package com.dongyang.android.youdongknowme.ui.view.cafeteria

import android.view.View
import androidx.core.content.ContextCompat
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ItemCalendarDayBinding
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CafeteriaContainer(
    view: View,
    private val calendarView: CalendarView,
    private val viewModel: CafeteriaViewModel,
) : ViewContainer(view), CalendarInterface {

    private val bind = ItemCalendarDayBinding.bind(view)
    private lateinit var day: CalendarDay

    private val dateFormatter = DateTimeFormatter.ofPattern("d")
    private val dayFormatter = DateTimeFormatter.ofPattern("EEE")
    private val monthFormatter = DateTimeFormatter.ofPattern("MMM")

    init {
        view.setOnClickListener {
            viewModel.selectedDate.value?.let { selectedDate ->
                if (selectedDate != day.date) {
                    notifyDateChanged(viewModel, calendarView, selectedDate, day.date)
                }
            }
        }
    }

    fun bind(day: CalendarDay) {
        this.day = day
        bind.apply {
            tvItemCalendarDate.text = dateFormatter.format(day.date)
            tvItemCalendarDay.text = dayFormatter.format(day.date)
            tvItemCalendarMonth.text = monthFormatter.format(day.date)
        }

        val (bgColor, textColor) = when (day.date) {
            viewModel.selectedDate.value -> R.color.blue300 to R.color.white
            LocalDate.now() -> R.color.gray200 to R.color.gray500
            else -> R.color.white to R.color.gray500
        }

        bind.mvItemCalendarContainer.setCardBackgroundColor(
            ContextCompat.getColor(
                view.context,
                bgColor
            )
        )
        setTextColor(textColor)
    }

    private fun setTextColor(colorRes: Int) {
        val color = ContextCompat.getColor(view.context, colorRes)

        bind.apply {
            tvItemCalendarDate.setTextColor(color)
            tvItemCalendarDay.setTextColor(color)
            tvItemCalendarMonth.setTextColor(color)
        }
    }
}