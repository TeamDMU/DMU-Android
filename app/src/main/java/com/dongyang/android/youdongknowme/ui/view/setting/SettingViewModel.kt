package com.dongyang.android.youdongknowme.ui.view.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dongyang.android.youdongknowme.data.repository.SettingRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel

/* 설정 뷰모델 */
class SettingViewModel(private val settingRepository: SettingRepository) : BaseViewModel() {

    private val _isAccessSchoolAlarm: MutableLiveData<Boolean> = MutableLiveData(true)
    val isAccessSchoolAlarm: LiveData<Boolean> get() = _isAccessSchoolAlarm

    private val _isAccessDepartAlarm: MutableLiveData<Boolean> = MutableLiveData(true)
    val isAccessDepartAlarm: LiveData<Boolean> get() = _isAccessDepartAlarm

    fun checkAccessAlarm() {
        val isAccessSchoolAlarm = settingRepository.getIsAccessSchoolAlarm()!!
        _isAccessSchoolAlarm.postValue(isAccessSchoolAlarm)

        val isAccessDepartAlarm = settingRepository.getIsAccessDepartAlarm()!!
        _isAccessDepartAlarm.postValue(isAccessDepartAlarm)
    }

    fun setIsAccessSchoolAlarm(isAccessSchoolAlarm: Boolean) {
        settingRepository.setIsAccessSchoolAlarm(isAccessSchoolAlarm)
    }

    fun setIsAccessDepartAlarm(isAccessDepartAlarm: Boolean) {
        settingRepository.setIsAccessDepartAlarm(isAccessDepartAlarm)
    }
}