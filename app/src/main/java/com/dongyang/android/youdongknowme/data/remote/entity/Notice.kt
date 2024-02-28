package com.dongyang.android.youdongknowme.data.remote.entity

import com.google.gson.annotations.SerializedName

data class Notice(
    @SerializedName("title")
    val title: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("url")
    val url: String,
)