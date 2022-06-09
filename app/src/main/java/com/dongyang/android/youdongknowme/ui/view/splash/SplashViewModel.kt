package com.dongyang.android.youdongknowme.ui.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dongyang.android.youdongknowme.data.repository.SplashRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel

class SplashViewModel(private val splashRepository: SplashRepository) : BaseViewModel() {
    private val _isFirstLaunch = MutableLiveData(false)
    val isFirstLaunch: LiveData<Boolean> get() = _isFirstLaunch

    fun checkFirstLaunch() {
        if (splashRepository.getIsFirstLaunch() == true) {
            _isFirstLaunch.value = true
        }
    }
}