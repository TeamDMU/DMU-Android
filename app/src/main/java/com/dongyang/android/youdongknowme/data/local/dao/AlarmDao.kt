package com.dongyang.android.youdongknowme.data.local.dao

import androidx.room.*
import com.dongyang.android.youdongknowme.data.local.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarm ORDER BY id DESC")
    fun getAllAlarm(): Flow<List<AlarmEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: AlarmEntity)

    @Query("SELECT count(*) FROM alarm WHERE isVisited = 0")
    fun getUnVisitedAlarmCount(): Flow<Int>

    @Query("UPDATE alarm SET isVisited = :isVisited WHERE id = :id")
    suspend fun updateIsVisitedAlarm(isVisited: Boolean, id: Int)
}