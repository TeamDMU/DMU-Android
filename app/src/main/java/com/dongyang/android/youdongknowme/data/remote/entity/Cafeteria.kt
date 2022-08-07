package com.dongyang.android.youdongknowme.data.remote.entity

import com.google.gson.annotations.SerializedName

data class Cafeteria(
    @SerializedName("range")
    var range: String,
    @SerializedName("date")
    var date: String,
    @SerializedName("restaurant")
    var restaurant: String,
    @SerializedName("menu_division")
    var menuDivision: String,
    @SerializedName("menu_content")
    var menuContent: String,
    @SerializedName("etc_info")
    var etcInfo: String
)
