package com.dongyang.android.youdongknowme.data.local.entity

data class OpenSourceEntity(
    val name: String,
    val url: String,
    val license: String,
) {
    companion object {
        val openSourceList = listOf<OpenSourceEntity>(
            OpenSourceEntity(
                "okhttp3",
                "https://github.com/square/okhttp/blob/master/LICENSE.txt",
                "Apache"
            ),
            OpenSourceEntity(
                "retrofit2",
                "https://github.com/square/retrofit/blob/master/LICENSE.txt",
                "Apache"
            ),
            OpenSourceEntity(
                "firebase",
                "https://firebase.google.com/terms",
                "Apache"
            ),
            OpenSourceEntity(
                "glide",
                "https://github.com/bumptech/glide/blob/master/LICENSE",
                "Apache"
            ),
            OpenSourceEntity(
                "gson",
                "https://github.com/google/gson/blob/master/LICENSE",
                "Apache"
            ),
            OpenSourceEntity(
                "AndroidViewAnimations",
                "https://github.com/daimajia/AndroidViewAnimations/blob/master/License",
                "MIT"
            ),
            OpenSourceEntity(
                "lottie",
                "https://github.com/airbnb/lottie-android/blob/master/LICENSE",
                "Apache"
            ),
            OpenSourceEntity(
                "MaterialCalendarView",
                "https://github.com/prolificinteractive/material-calendarview/blob/master/LICENSE",
                "MIT"
            ),
            OpenSourceEntity(
                "CalendarView",
                "https://github.com/kizitonwose/CalendarView/blob/master/LICENSE.md",
                "MIT"
            ),
        )
    }
}