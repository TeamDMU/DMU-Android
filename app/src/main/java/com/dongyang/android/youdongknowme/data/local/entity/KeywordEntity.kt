package com.dongyang.android.youdongknowme.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keyword")
data class KeywordEntity(
    @PrimaryKey @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "englishName") var englishName: String = "",
    @ColumnInfo(name = "isSubscribe") var isSubscribe: Boolean = false,
)