package com.dongyang.android.youdongknowme.data.remote.entity

import com.google.gson.annotations.SerializedName

data class Notice(
    @SerializedName("num")
    var num: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("writer")
    var writer: String,
    @SerializedName("date")
    var date: String,
)