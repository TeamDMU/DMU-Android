package com.dongyang.android.youdongknowme.ui.view.cafeteria

import android.view.View
import androidx.core.content.ContextCompat
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ItemCalendarDayBinding
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.ViewContainer
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
            if (viewModel.selectedDate.value != day.date) {
                notifyDateChanged(viewModel, calendarView, viewModel.selectedDate.value, day.date)
            }
        }
    }

    fun bind(day: CalendarDay) {
        this.day = day
        bind.itemCalendarDate.text = dateFormatter.format(day.date)
        bind.itemCalendarDay.text = dayFormatter.format(day.date)
        bind.itemCalendarMonth.text = monthFormatter.format(day.date)

        if (day.date == viewModel.selectedDate.value) {
            bind.mvItemCalendarDate.setCardBackgroundColor(
                ContextCompat.getColor(
                    view.context,
                    R.color.blue300
                )
            )
            bind.itemCalendarDate.setTextColor(ContextCompat.getColor(view.context, R.color.white))
        } else {
            bind.mvItemCalendarDate.setCardBackgroundColor(
                ContextCompat.getColor(
                    view.context,
                    R.color.white
                )
            )
            bind.itemCalendarDate.setTextColor(ContextCompat.getColor(view.context, R.color.black))
        }
    }
}
