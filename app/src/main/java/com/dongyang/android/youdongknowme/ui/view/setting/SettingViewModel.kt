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

/* 설정 뷰모델 */
class SettingViewModel(private val settingRepository: SettingRepository) : BaseViewModel() {

    private val _errorState: MutableLiveData<Event<Int>> = MutableLiveData()
    val errorState: LiveData<Event<Int>> = _errorState

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = _isError

    private val _isAccessUniversityAlarm: MutableLiveData<Boolean> = MutableLiveData()
    val isAccessUniversityAlarm: LiveData<Boolean> get() = _isAccessUniversityAlarm

    private val _isAccessDepartAlarm: MutableLiveData<Boolean> = MutableLiveData()
    val isAccessDepartAlarm: LiveData<Boolean> get() = _isAccessDepartAlarm

    private val _myDepartment: MutableLiveData<String> = MutableLiveData()
    val myDepartment: LiveData<String> get() = _myDepartment

    private val _myTopics: MutableLiveData<List<String>> = MutableLiveData()
    val myTopics: LiveData<List<String>> get() = _myTopics

    private var FCMToken: String = ""

    init {
        checkAccessAlarm()
        getUserFCMToken()
        getUserTopic()
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
        checkAccessAlarm()
    }

    fun setIsAccessDepartAlarm(isAccessDepartAlarm: Boolean) {
        settingRepository.setIsAccessDepartAlarm(isAccessDepartAlarm)
        checkAccessAlarm()
    }

    fun getUserDepartment() {
        _myDepartment.value = settingRepository.getUserDepartment()
    }

    private fun getUserTopic() {
        viewModelScope.launch {
            val keyword = settingRepository.getUserTopic()
            _myTopics.value = keyword
        }
    }

    private fun getUserFCMToken() {
        FCMToken = settingRepository.getUserFCMToken()
    }

    fun updateUserTopic() {
        _isLoading.postValue(true)

        viewModelScope.launch {
            val result = settingRepository.updateUserTopic(
                UpdateTopic(
                    token = FCMToken,
                    topics = myTopics.value ?: emptyList()
                )
            )

            when (result) {
                is NetworkResult.Success -> {
                    setIsAccessUniversityAlarm(true)
                    _isLoading.postValue(false)
                    _isError.postValue(false)
                }

                is NetworkResult.Error -> {
                    handleError(result, _errorState)
                    setIsAccessUniversityAlarm(false)
                    _isLoading.postValue(false)
                    _isError.postValue(true)
                }
            }
        }
    }

    fun removeUserTopic() {
        _isLoading.postValue(true)

        viewModelScope.launch {
            val result =
                settingRepository.removeUserTopic(
                    RemoveToken(token = FCMToken)
                )

            when (result) {
                is NetworkResult.Success -> {
                    setIsAccessUniversityAlarm(false)
                    _isLoading.postValue(false)
                    _isError.postValue(false)
                }

                is NetworkResult.Error -> {
                    handleError(result, _errorState)
                    setIsAccessUniversityAlarm(false)
                    _isLoading.postValue(false)
                    _isError.postValue(true)
                }
            }
        }
    }

    fun updateUserDepartment() {
        val department = _myDepartment.value ?: run {
            _isError.postValue(true)
            setIsAccessDepartAlarm(false)
            return
        }

        _isLoading.postValue(true)

        viewModelScope.launch {
            val result = settingRepository.updateUserDepartment(
                UpdateDepartment(
                    token = FCMToken,
                    department = department
                )
            )

            when (result) {
                is NetworkResult.Success -> {
                    setIsAccessDepartAlarm(true)
                    _isLoading.postValue(false)
                    _isError.postValue(false)
                }

                is NetworkResult.Error -> {
                    handleError(result, _errorState)
                    setIsAccessDepartAlarm(false)
                    _isLoading.postValue(false)
                    _isError.postValue(true)
                }
            }
        }
    }

    fun removeUserDepartment() {
        _isLoading.postValue(true)

        viewModelScope.launch {
            val result = settingRepository.removeUserDepartment(
                RemoveToken(token = FCMToken)
            )

            when (result) {
                is NetworkResult.Success -> {
                    setIsAccessDepartAlarm(false)
                    _isLoading.postValue(false)
                    _isError.postValue(false)
                }

                is NetworkResult.Error -> {
                    handleError(result, _errorState)
                    setIsAccessDepartAlarm(false)
                    _isLoading.postValue(false)
                    _isError.postValue(true)
                }
            }
        }
    }
}
