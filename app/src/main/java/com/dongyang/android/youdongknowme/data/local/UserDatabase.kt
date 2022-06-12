package com.dongyang.android.youdongknowme.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.dongyang.android.youdongknowme.data.local.dao.AlarmDao
import com.dongyang.android.youdongknowme.data.local.dao.KeywordDao
import com.dongyang.android.youdongknowme.data.local.entity.AlarmEntity
import com.dongyang.android.youdongknowme.data.local.entity.KeywordEntity

@Database(entities = [KeywordEntity::class, AlarmEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun keywordDao(): KeywordDao
    abstract fun alarmDao(): AlarmDao
}