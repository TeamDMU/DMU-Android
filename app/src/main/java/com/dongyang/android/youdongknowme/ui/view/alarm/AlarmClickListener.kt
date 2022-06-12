package com.dongyang.android.youdongknowme.ui.view.alarm

import com.dongyang.android.youdongknowme.data.local.entity.AlarmEntity

interface AlarmClickListener {
    fun itemClick(alarmEntity: AlarmEntity)
}