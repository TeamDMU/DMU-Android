package com.dongyang.android.youdongknowme.ui.view.permission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.data.remote.entity.RemoveToken
import com.dongyang.android.youdongknowme.data.remote.entity.UpdateDepartment
import com.dongyang.android.youdongknowme.data.remote.entity.UpdateTopic
import com.dongyang.android.youdongknowme.data.repository.SettingRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.ui.view.util.Event
import kotlinx.coroutines.launch

/* 설정 뷰모델 */
class PermissionViewModel(private val settingRepository: SettingRepository) : BaseViewModel() {

    private val _errorState: MutableLiveData<Event<Int>> = MutableLiveData()
    val errorState: LiveData<Event<Int>> = _errorState

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = _isError

    private val _isAccessUniversityAlarm: MutableLiveData<Boolean> = MutableLiveData(false)
    val isAccessUniversityAlarm: LiveData<Boolean> get() = _isAccessUniversityAlarm

    private val _isAccessDepartAlarm: MutableLiveData<Boolean> = MutableLiveData(false)
    val isAccessDepartAlarm: LiveData<Boolean> get() = _isAccessDepartAlarm

    private val _myDepartment: MutableLiveData<String> = MutableLiveData()
    val myDepartment: LiveData<String> get() = _myDepartment

    fun checkAccessAlarm() {
        val isAccessUniversityAlarm = settingRepository.getIsAccessUniversityAlarm()
        _isAccessUniversityAlarm.postValue(isAccessUniversityAlarm)

        val isAccessDepartAlarm = settingRepository.getIsAccessDepartAlarm()
        _isAccessDepartAlarm.postValue(isAccessDepartAlarm)
    }

    fun setIsAccessUniversityAlarm(isAccessSchoolAlarm: Boolean) {
        settingRepository.setIsAccessSchoolAlarm(isAccessSchoolAlarm)
    }

    fun setIsAccessDepartAlarm(isAccessDepartAlarm: Boolean) {
        settingRepository.setIsAccessDepartAlarm(isAccessDepartAlarm)
    }
}