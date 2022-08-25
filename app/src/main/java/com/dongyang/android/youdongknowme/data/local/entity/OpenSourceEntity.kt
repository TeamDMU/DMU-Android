package com.dongyang.android.youdongknowme.data.local.entity

data class OpenSourceEntity (
    val name : String,
    val url : String,
) {
    companion object {
        val openSourceList = listOf<OpenSourceEntity>(
            OpenSourceEntity(
                "okhttp3",
                "https://github.com/square/okhttp/blob/master/LICENSE.txt"
            ),
            OpenSourceEntity(
                "retrofit2",
                "https://github.com/square/retrofit/blob/master/LICENSE.txt"
            ),
            OpenSourceEntity(
                "firebase",
                "https://firebase.google.com/terms"
            ),
            OpenSourceEntity(
                "glide",
                "https://github.com/bumptech/glide/blob/master/LICENSE"
            ),
            OpenSourceEntity(
                "gson",
                "https://github.com/google/gson/blob/master/LICENSE"
            ),
            OpenSourceEntity(
                "AndroidViewAnimations",
                "https://github.com/daimajia/AndroidViewAnimations/blob/master/License"
            ),
            OpenSourceEntity(
                "lottie",
                "https://github.com/airbnb/lottie-android/blob/master/LICENSE"
            ),
            OpenSourceEntity(
                "MaterialCalendarView",
                "https://github.com/prolificinteractive/material-calendarview/blob/master/LICENSE"
            ),
            OpenSourceEntity(
                "CalendarView",
                "https://github.com/kizitonwose/CalendarView/blob/master/LICENSE.md"
            ),
        )
    }
}