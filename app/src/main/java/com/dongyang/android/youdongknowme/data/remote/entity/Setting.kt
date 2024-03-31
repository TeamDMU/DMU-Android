package com.dongyang.android.youdongknowme.data.remote.entity

data class UpdateDepartment(
    val token: String,
    val department: String,
)

data class RemoveToken(
    val token: String,
)

data class UpdateTopic(
    val token: String,
    val topics: List<String>,
)