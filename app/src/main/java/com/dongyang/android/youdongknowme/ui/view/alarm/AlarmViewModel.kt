package com.dongyang.android.youdongknowme.ui.view.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.data.local.entity.AlarmEntity
import com.dongyang.android.youdongknowme.data.repository.AlarmRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AlarmViewModel(private val alarmRepository: AlarmRepository) : BaseViewModel() {

    private val _alarmList: MutableLiveData<List<AlarmEntity>> = MutableLiveData()
    val alarmList: LiveData<List<AlarmEntity>> get() = _alarmList

    fun getAlarms() {
        viewModelScope.launch {
            alarmRepository.getUserAlarms().collect { alarmList ->
                _alarmList.postValue(alarmList)
            }
        }
    }

    fun insertAlarm() {
        val testAlarm = AlarmEntity(null, "타이틀 테스트입니다.", "컴퓨터소프트웨어공학과", "시험", 10, false)
        viewModelScope.launch {
            alarmRepository.insertAlarm(testAlarm)
        }
    }

}