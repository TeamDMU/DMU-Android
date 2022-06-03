package com.dongyang.android.youdongknowme.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.dongyang.android.youdongknowme.data.local.dao.UserDao
import com.dongyang.android.youdongknowme.data.local.entity.KeywordEntity

@Database(entities = [KeywordEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}