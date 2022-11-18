package com.dongyang.android.youdongknowme.ui.view.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.data.local.SharedPreference.NO_SCHEDULE
import com.dongyang.android.youdongknowme.data.remote.entity.Schedule
import com.dongyang.android.youdongknowme.data.repository.ScheduleRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.ui.view.util.Event
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.launch


/* 학사 일정 뷰모델 */
class ScheduleViewModel(private val scheduleRepository: ScheduleRepository) : BaseViewModel() {

    private val _errorState: MutableLiveData<Event<Int>> = MutableLiveData()
    val errorState: LiveData<Event<Int>> = _errorState

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = _isError

    private val _scheduleList = MutableLiveData<List<Schedule>>()
    val scheduleList: LiveData<List<Schedule>> = _scheduleList

    private val _pickYear = MutableLiveData<Int>()

    private val _pickMonth = MutableLiveData<Int>()
    val pickMonth: LiveData<Int> = _pickMonth

    fun setPickedDate(date: CalendarDay) {
        _pickYear.value = date.year
        _pickMonth.value = date.month
    }

    fun getSchedules() {
        // 로컬에 저장한 데이터가 없으면 네트워크에서 데이터를 받아와 로컬에 저장 및 화면에 출력
        if (scheduleRepository.getLocalSchedules() == NO_SCHEDULE) {
            _isLoading.postValue(true)
            viewModelScope.launch {
                when (val result = scheduleRepository.fetchSchedules()) {
                    is NetworkResult.Success -> {
                        val scheduleList = result.data
                        // 선택한 연월 조건에 따라 리스트 출력
                        _scheduleList.postValue(scheduleList.filter { it.month == pickMonth.value && it.year == _pickYear.value.toString() })
                        scheduleRepository.setLocalSchedules(Gson().toJson(scheduleList))
                        _isError.postValue(false)
                        _isLoading.postValue(false)
                    }
                    is NetworkResult.Error -> {
                        handleError(result, _errorState)
                        _isError.postValue(true)
                        _isLoading.postValue(false)
                    }
                }
            }
        } else {
            // 저장한 데이터가 있으면 로컬에서 곧바로 데이터를 받아와 화면에 출력
            val localSchedules = scheduleRepository.getLocalSchedules()
            val scheduleList =
                Gson().fromJson<List<Schedule>>(
                    localSchedules,
                    object : TypeToken<List<Schedule>>() {}.type
                ).filter {
                    // 선택한 연월 조건에 따라 리스트 출력
                    it.month == pickMonth.value && it.year == _pickYear.value.toString()
                }

            _isError.postValue(false)
            _scheduleList.postValue(scheduleList)
        }
    }
}