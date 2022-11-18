package com.dongyang.android.youdongknowme.data.local.entity

data class OpenSourceEntity(
    val name: String,
    val url: String,
    val license: String,
) {
    companion object {
        val openSourceList = listOf<OpenSourceEntity>(
            OpenSourceEntity(
                "Firebase",
                "https://firebase.google.com/terms",
                "Apache License"
            ),
            OpenSourceEntity(
                "glide",
                "https://github.com/bumptech/glide/blob/master/LICENSE",
                "Apache License"
            ),
            OpenSourceEntity(
                "okhttp",
                "https://github.com/square/okhttp/blob/master/LICENSE.txt",
                "Apache License"
            ),
            OpenSourceEntity(
                "retrofit",
                "https://github.com/square/retrofit/blob/master/LICENSE.txt",
                "Apache License"
            ),
            OpenSourceEntity(
                "gson",
                "https://github.com/google/gson/blob/master/LICENSE",
                "Apache License"
            ),
            OpenSourceEntity(
                "AndroidViewAnimations",
                "https://github.com/daimajia/AndroidViewAnimations/blob/master/License",
                "MIT License"
            ),
            OpenSourceEntity(
                "lottie",
                "https://github.com/airbnb/lottie-android/blob/master/LICENSE",
                "Apache License"
            ),
            OpenSourceEntity(
                "MaterialCalendarView",
                "https://github.com/prolificinteractive/material-calendarview/blob/master/LICENSE",
                "MIT License"
            ),
            OpenSourceEntity(
                "CalendarView",
                "https://github.com/kizitonwose/CalendarView/blob/master/LICENSE.md",
                "MIT License"
            ),
            OpenSourceEntity(
                "flexbox-layout",
                "https://github.com/google/flexbox-layout/blob/main/LICENSE",
                "Apache License"
            ),
        )
    }
}