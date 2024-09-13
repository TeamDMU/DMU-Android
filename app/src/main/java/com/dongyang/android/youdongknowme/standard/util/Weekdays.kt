package com.dongyang.android.youdongknowme.standard.util

import java.time.DayOfWeek

enum class Weekdays {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY;

    companion object {

        fun from(dayOfWeek: DayOfWeek): Weekdays {
            return when (dayOfWeek) {
                DayOfWeek.MONDAY -> MONDAY
                DayOfWeek.TUESDAY -> TUESDAY
                DayOfWeek.WEDNESDAY -> WEDNESDAY
                DayOfWeek.THURSDAY -> THURSDAY
                DayOfWeek.FRIDAY -> FRIDAY
                else -> throw IllegalArgumentException("에러")
            }
        }
    }
}