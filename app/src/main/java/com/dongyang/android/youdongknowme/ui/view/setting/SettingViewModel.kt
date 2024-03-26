package com.dongyang.android.youdongknowme.ui.view.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dongyang.android.youdongknowme.data.repository.SettingRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel

/* 설정 뷰모델 */
class SettingViewModel(private val settingRepository: SettingRepository) : BaseViewModel() {

    private val _isAccessUniversityAlarm: MutableLiveData<Boolean> = MutableLiveData(false)
    val isAccessUniversityAlarm: LiveData<Boolean> get() = _isAccessUniversityAlarm

    private val _isAccessDepartAlarm: MutableLiveData<Boolean> = MutableLiveData(false)
    val isAccessDepartAlarm: LiveData<Boolean> get() = _isAccessDepartAlarm

    private val _myDepartment: MutableLiveData<String> = MutableLiveData()
    val myDepartment: LiveData<String> get() = _myDepartment

    fun checkAccessAlarm() {
        val isAccessSchoolAlarm = settingRepository.getIsAccessSchoolAlarm()
        _isAccessUniversityAlarm.postValue(isAccessSchoolAlarm)

        val isAccessDepartAlarm = settingRepository.getIsAccessDepartAlarm()
        _isAccessDepartAlarm.postValue(isAccessDepartAlarm)
    }

    fun setIsAccessSchoolAlarm(isAccessSchoolAlarm: Boolean) {
        settingRepository.setIsAccessSchoolAlarm(isAccessSchoolAlarm)
    }

    fun setIsAccessDepartAlarm(isAccessDepartAlarm: Boolean) {
        settingRepository.setIsAccessDepartAlarm(isAccessDepartAlarm)
    }

    fun getUserDepartment() {
        val myDepartment = settingRepository.getUserDepartment()
        _myDepartment.postValue(myDepartment)
    }
}