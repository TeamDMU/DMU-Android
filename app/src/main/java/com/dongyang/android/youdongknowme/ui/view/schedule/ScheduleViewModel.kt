package com.dongyang.android.youdongknowme.ui.view.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.data.remote.entity.Schedule
import com.dongyang.android.youdongknowme.data.repository.ScheduleRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    private val _errorState: MutableLiveData<Int> = MutableLiveData()
    val errorState: LiveData<Int> = _errorState

    fun setPickedDate(date: CalendarDay) {
        _pickYear.value = date.year
        _pickMonth.value = date.month
    }

    fun getSchedules() {
        // 로컬에 저장한 데이터가 없으면 네트워크에서 데이터를 받아와 로컬에 저장 및 화면에 출력
        if (scheduleRepository.getLocalSchedules() == "No Data") {
            _isLoading.postValue(true)
            try {
                viewModelScope.launch(connectionHandler) {
                    val response = scheduleRepository.fetchSchedules()

                    if (response.isSuccessful) {
                        // 선택한 연월 조건에 따라 리스트 출력
                        _scheduleList.postValue(response.body()?.filter { it.month == pickMonth.value && it.year == pickYear.value.toString() })
                        scheduleRepository.setLocalSchedules(Gson().toJson(response.body()))
                    } else {
                        _errorState.postValue(ERROR_SCHEDULE)
                    }
                    _isLoading.postValue(false)
                }
            } catch (e: Exception) {
                _isLoading.postValue(false)
                _errorState.postValue(ERROR_SCHEDULE)
            }
        } else {
            // 저장한 데이터가 있으면 로컬에서 곧바로 데이터를 받아와 화면에 출력
            val tmp = scheduleRepository.getLocalSchedules()
            val scheduleList =
                Gson().fromJson<List<Schedule>>(tmp, object : TypeToken<List<Schedule>>() {}.type).filter {
                    // 선택한 연월 조건에 따라 리스트 출력
                    it.month == pickMonth.value && it.year == pickYear.value.toString()
                }

            _scheduleList.postValue(scheduleList)
        }
    }

    companion object {
        const val ERROR_SCHEDULE = R.string.error_schedule
    }
}