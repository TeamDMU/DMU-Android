package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.local.dao.AlarmDao
import com.dongyang.android.youdongknowme.data.local.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow

class AlarmRepository(
    private val alarmDao: AlarmDao
) {
    fun getUserAlarms(): Flow<List<AlarmEntity>> {
        return alarmDao.getAllAlarm()
    }

    suspend fun insertAlarm(alarmEntity: AlarmEntity) {
        alarmDao.insertAlarm(alarmEntity)
    }

    suspend fun updateIsVisitedAlarm(isVisited: Boolean, id: Int) {
        alarmDao.updateIsVisitedAlarm(isVisited, id)
    }
}