package com.dongyang.android.youdongknowme.ui.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.data.remote.entity.Token
import com.dongyang.android.youdongknowme.data.repository.MainRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.ui.view.util.Event
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(private val mainRepository: MainRepository) : BaseViewModel() {
    private val _errorState: MutableLiveData<Event<Int>> = MutableLiveData()
    val errorState: LiveData<Event<Int>> = _errorState

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = _isError

    private val _myDepartment: MutableLiveData<String> = MutableLiveData()
    val myDepartment: LiveData<String> get() = _myDepartment

    private val _myTopics: MutableLiveData<List<String>> = MutableLiveData()
    val myTopics: LiveData<List<String>> get() = _myTopics

    private val _FCMToken: MutableLiveData<String> = MutableLiveData()
    val FCMToken: LiveData<String> get() = _FCMToken

    private val _isFirstLaunch: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFirstLaunch: LiveData<Boolean> get() = _isFirstLaunch

    init{
        getUserDepart()
        getUserTopic()
    }

    fun checkFirstLaunch() {
        if (mainRepository.getIsFirstLaunch()) {
            _isFirstLaunch.value = true
        }
    }

    fun setFCMToken(token: String){
        mainRepository.setFCMToken(token)
        _FCMToken.value = token
    }

    private fun getUserDepart(){
        _myDepartment.value = mainRepository.getUserDepartment()
    }

    private fun getUserTopic(){
        viewModelScope.launch {
            _myTopics.value = mainRepository.getUserTopic()
        }
    }

    fun setInitToken() {
        _isLoading.postValue(true)

        viewModelScope.launch {
            when (val result = mainRepository.setUserToken(Token(
                token = FCMToken.value.toString(),
                department = myDepartment.value ?: "",
                topics = myTopics.value ?: emptyList()
            )
            )) {
                is NetworkResult.Success -> {
                    Timber.d("first ${FCMToken.value.toString()}")
                    mainRepository.setIsFirstLaunch(false)
                    _isFirstLaunch.postValue(false)
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