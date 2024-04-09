package com.dongyang.android.youdongknowme.ui.view.setting

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
import timber.log.Timber

/* 설정 뷰모델 */
class SettingViewModel(private val settingRepository: SettingRepository) : BaseViewModel() {

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

    private val _myTopics: MutableLiveData<List<String>> = MutableLiveData()
    val myTopics: LiveData<List<String>> get() = _myTopics

    private val _FCMToken: MutableLiveData<String> = MutableLiveData()
    private val FCMToken: LiveData<String> get() = _FCMToken

    init {
        checkAccessAlarm()
        getUserFCMToken()
        getUserDepartment()
    }

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

    fun getUserDepartment() {
        _myDepartment.value = settingRepository.getUserDepartment()
    }

    fun getUserTopic() {
        viewModelScope.launch {
            val keyword = settingRepository.getUserTopic()
            _myTopics.value = keyword
        }
    }

    private fun getUserFCMToken() {
        _FCMToken.value = settingRepository.getUserFCMToken()
    }

    fun updateUserDepartment(department: String) {
        _isLoading.postValue(true)

        viewModelScope.launch {
            when (val result = settingRepository.updateUserDepartment(
                UpdateDepartment(
                    token = FCMToken.value.toString(),
                    department = department
                )
            )) {
                is NetworkResult.Success -> {
                    Timber.d(department)
                    setIsAccessDepartAlarm(true)
                    _isLoading.postValue(false)
                    _isError.postValue(false)
                }

                is NetworkResult.Error -> {
                    handleError(result, _errorState)
                    _isLoading.postValue(false)
                    _isError.postValue(true)
                }
            }
        }
    }

    fun removeUserDepartment() {
        _isLoading.postValue(true)

        viewModelScope.launch {
            when (val result =
                settingRepository.removeUserDepartment(
                    RemoveToken(token = FCMToken.value.toString())
                )
            ) {
                is NetworkResult.Success -> {
                    setIsAccessDepartAlarm(false)
                    _isLoading.postValue(false)
                    _isError.postValue(false)
                }

                is NetworkResult.Error -> {
                    handleError(result, _errorState)
                    _isLoading.postValue(false)
                    _isError.postValue(true)
                }
            }
        }
    }

    fun updateUserTopic() {
        _isLoading.postValue(true)

        viewModelScope.launch {
            when (val result = settingRepository.updateUserTopic(
                UpdateTopic(
                    token = FCMToken.value.toString(),
                    topics = myTopics.value ?: emptyList()
                )
            )) {
                is NetworkResult.Success -> {
                    Timber.d("${myTopics.value}")
                    setIsAccessUniversityAlarm(true)
                    _isLoading.postValue(false)
                    _isError.postValue(false)
                }

                is NetworkResult.Error -> {
                    handleError(result, _errorState)
                    _isLoading.postValue(false)
                    _isError.postValue(true)
                }
            }
        }
    }

    fun removeUserTopic() {
        _isLoading.postValue(true)

        viewModelScope.launch {
            when (val result =
                settingRepository.removeUserTopic(
                    RemoveToken(token = FCMToken.value.toString())
                )) {
                is NetworkResult.Success -> {
                    setIsAccessUniversityAlarm(false)
                    _isLoading.postValue(false)
                    _isError.postValue(false)
                }

                is NetworkResult.Error -> {
                    handleError(result, _errorState)
                    _isLoading.postValue(false)
                    _isError.postValue(true)
                }
            }
        }
    }
}