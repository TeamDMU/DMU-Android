package com.dongyang.android.youdongknowme.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    var id: Int?,
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "department")
    var department: String = "",
    @ColumnInfo(name = "keyword")
    var keyword: String = "",
    @ColumnInfo(name = "num")
    var num: Int,
    @ColumnInfo(name = "isVisited")
    var isVisited: Boolean = false,
)