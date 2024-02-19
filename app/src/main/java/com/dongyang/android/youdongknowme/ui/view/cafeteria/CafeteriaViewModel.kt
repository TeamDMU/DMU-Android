package com.dongyang.android.youdongknowme.ui.view.cafeteria

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.data.remote.entity.Cafeteria
import com.dongyang.android.youdongknowme.data.repository.CafeteriaRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.ui.view.util.Event
import com.dongyang.android.youdongknowme.ui.view.util.ResourceProvider
import kotlinx.coroutines.launch
import java.time.LocalDate

class CafeteriaViewModel(
    private val cafeteriaRepository: CafeteriaRepository,
    private val resourceProvider: ResourceProvider,
) : BaseViewModel() {

    private val _errorState: MutableLiveData<Event<Int>> = MutableLiveData()
    val errorState: LiveData<Event<Int>> = _errorState

    private val _isError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = _isError

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _selectedDate: MutableLiveData<LocalDate> = MutableLiveData(LocalDate.now())
    val selectedDate: LiveData<LocalDate> = _selectedDate

    private val _cafeteriaList: MutableLiveData<List<Cafeteria>> = MutableLiveData()
    val cafeteriaList: LiveData<List<Cafeteria>> = _cafeteriaList

    private val _menus: MutableLiveData<List<String>> = MutableLiveData()
    val menus: LiveData<List<String>> = _menus

    init {
        fetchCafeteria()
    }

    fun fetchCafeteria() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            when (val result = cafeteriaRepository.fetchMenuList()) {
                is NetworkResult.Success -> {
                    val menuList = result.data
                    _cafeteriaList.value = menuList
                    updateSelectedDate(LocalDate.now())
                    _isError.postValue(false)
                    _isLoading.postValue(false)
                }

                is NetworkResult.Error -> {
                    handleError(result, _errorState)
                    _isError.postValue(true)
                    _isLoading.postValue(false)
                }
            }
        }
    }

    fun updateSelectedDate(selectedDate: LocalDate) {
        _selectedDate.value = selectedDate
        updateMenuList(selectedDate.toString())
    }

    private fun updateMenuList(selectedDate: String) {
        val cafeteriaList = _cafeteriaList.value ?: emptyList()

        cafeteriaList.forEach { it.date = it.date.substring(0 until 10) }


        val menu = cafeteriaList.find {
            it.date == selectedDate
        }?.menu ?: listOf<String>()

        _menus.value = parsingMenu(menu)
    }

    private fun parsingMenu(menu: List<String>): List<String> {
        val menus = menu.ifEmpty {
            listOf(resourceProvider.getString(R.string.cafeteria_no_menu))
        }

        return menus
    }
}
