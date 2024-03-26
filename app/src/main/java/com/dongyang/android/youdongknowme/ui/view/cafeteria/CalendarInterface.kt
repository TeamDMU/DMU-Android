package com.dongyang.android.youdongknowme.ui.view.cafeteria

import com.kizitonwose.calendarview.CalendarView
import java.time.LocalDate

interface CalendarInterface {
    fun notifyDateChanged(
        viewModel: CafeteriaViewModel,
        calendarView: CalendarView,
        oldDate: LocalDate?,
        selectedDate: LocalDate
    ) {
        viewModel.updateMenuList(selectedDate)
        calendarView.notifyDateChanged(selectedDate)
        oldDate?.let { calendarView.notifyDateChanged(it) }
    }
}
