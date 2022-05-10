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

data class NoticeDetail(
    @SerializedName("num")
    var num: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("writer")
    var writer: String,
    @SerializedName("date")
    var date: String,
    @SerializedName("content")
    var content: String,
    @SerializedName("img_url")
    var imgUrl : List<String>,
    @SerializedName("file_url")
    var fileUrl : List<String>
    )