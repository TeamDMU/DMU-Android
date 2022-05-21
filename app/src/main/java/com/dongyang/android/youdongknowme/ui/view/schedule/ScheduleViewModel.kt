package com.dongyang.android.youdongknowme.ui.view.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.data.remote.entity.Schedule
import com.dongyang.android.youdongknowme.data.repository.ScheduleRepository
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.launch

/* 학사 일정 뷰모델 */
class ScheduleViewModel(private val scheduleRepository: ScheduleRepository) : BaseViewModel() {

    private val _scheduleList = MutableLiveData<List<Schedule>>()
    val scheduleList: LiveData<List<Schedule>> get() = _scheduleList

    private val _pickYear = MutableLiveData<Int>()
    val pickYear: LiveData<Int> get() = _pickYear

    private val _pickMonth = MutableLiveData<Int>()
    val pickMonth: LiveData<Int> get() = _pickMonth

    fun setPickDate(date: CalendarDay) {
        _pickYear.value = date.year
        _pickMonth.value = date.month
    }

    fun getScheduleList() {
        _isLoading.postValue(true)
        try {
            viewModelScope.launch(connectionHandler) {
                val response = scheduleRepository.getNoticeList()

                if(response.isSuccessful) {
                    val scheduleList = response.body()!!.filter {
                        it.year == pickYear.value.toString() &&
                        it.month == pickMonth.value
                    }

                    _scheduleList.postValue(scheduleList)
                }
                _isLoading.postValue(false)
            }
        } catch (e: Exception) {
            _isLoading.postValue(false)
        }
    }
}