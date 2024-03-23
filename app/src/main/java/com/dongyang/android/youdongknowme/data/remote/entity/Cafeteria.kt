package com.dongyang.android.youdongknowme.data.remote.entity

import com.google.gson.annotations.SerializedName

data class Cafeteria(
    @SerializedName("date")
    val date: String,
    @SerializedName("menus")
    val menus: List<String>
)
