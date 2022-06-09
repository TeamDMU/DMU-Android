package com.dongyang.android.youdongknowme.ui.view.splash

import com.dongyang.android.youdongknowme.data.repository.SplashRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel

class SplashViewModel(private val splashRepository: SplashRepository) : BaseViewModel() {

    private var _isFirstLaunch: Boolean = false
    val isFirstLaunch: Boolean get() = _isFirstLaunch

    fun checkFirstLaunch() {
        if (splashRepository.getIsFirstLaunch() == true) {
            _isFirstLaunch = true
        }
    }
}