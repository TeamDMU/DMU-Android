package com.dongyang.android.youdongknowme.ui.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dongyang.android.youdongknowme.data.repository.DetailRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.ui.view.util.Event

class DetailViewModel(private val detailRepository: DetailRepository) : BaseViewModel() {

    private val _errorState: MutableLiveData<Event<Int>> = MutableLiveData()
    val errorState: LiveData<Event<Int>> = _errorState

    private val _isError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = _isError

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> get() = _isLoading

    companion object {
        private const val DEFAULT_VALUE = 0
    }
}