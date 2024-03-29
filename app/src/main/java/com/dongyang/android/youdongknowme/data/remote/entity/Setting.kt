package com.dongyang.android.youdongknowme.data.remote.entity

data class UpdateDepartment(
    val token: String,
    val department: String,
)

data class RemoveDepartment(
    val token: String,
)