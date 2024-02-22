package com.dongyang.android.youdongknowme.data.remote.entity

import com.google.gson.annotations.SerializedName

data class Cafeteria(
    @SerializedName("date")
    var date: String,
    @SerializedName("menus")
    val menu: List<String>
)
