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

    private val _title: MutableLiveData<String> = MutableLiveData()
    val title: LiveData<String> get() = _title

    private val _department: MutableLiveData<String> = MutableLiveData()
    val department: LiveData<String> get() = _department

    private val _keyword: MutableLiveData<String> = MutableLiveData()
    val keyword: LiveData<String> get() = _keyword

    fun getAlarms() {
        viewModelScope.launch {
            alarmRepository.getUserAlarms().collect { alarmList ->
                _alarmList.postValue(alarmList)
            }
        }
    }

    fun updateIsVisitedAlarm(isVisited: Boolean, id: Int) {
        viewModelScope.launch {
            alarmRepository.updateIsVisitedAlarm(isVisited, id)
        }
    }
}