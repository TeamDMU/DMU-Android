package com.dongyang.android.youdongknowme.ui.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dongyang.android.youdongknowme.data.repository.SplashRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel

class SplashViewModel(private val splashRepository: SplashRepository) : BaseViewModel() {
    private val _isDepart = MutableLiveData(false)
    val isDepart: LiveData<Boolean> get() = _isDepart

    fun checkDepart() {
        if (splashRepository.getDepartment() == "")
            _isDepart.postValue(false)
        else
            _isDepart.postValue(true)
    }
}