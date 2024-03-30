package com.dongyang.android.youdongknowme.ui.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.data.remote.entity.Token
import com.dongyang.android.youdongknowme.data.repository.KeywordRepository
import com.dongyang.android.youdongknowme.data.repository.MainRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.ui.view.util.Event
import com.google.firebase.messaging.FirebaseMessaging
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
    fun checkFirstLaunch() {
        if (mainRepository.getIsFirstLaunch()) {
            _isFirstLaunch.value = true
        }
    }

    fun issuedToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _FCMToken.value = task.result
                setInitToken()
            } else {
                _isError.value = true
            }
        }
    }

    fun setInitToken() {
        _isLoading.postValue(true)

        val department = mainRepository.getUserDepartment()
        _myDepartment.postValue(department)

        viewModelScope.launch {

            val keyword = mainRepository.getUserTopic()
            _myTopics.value = keyword

            val token = Token(
                token = FCMToken.value.toString(),
                department = myDepartment.value ?: "",
                topics = myTopics.value ?: emptyList()
            )

            Timber.tag("initToken").d("$token")

            when (val result = mainRepository.setUserToken(token)) {
                is NetworkResult.Success -> {
                    mainRepository.setIsFirstLaunch(false)
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