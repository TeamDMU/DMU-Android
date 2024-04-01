package com.dongyang.android.youdongknowme.data.remote.entity

import com.google.gson.annotations.SerializedName

data class Token (
    @SerializedName("token")
    val token: String,
    @SerializedName("department")
    val department: String,
    @SerializedName("topics")
    val topics: List<String>,
)