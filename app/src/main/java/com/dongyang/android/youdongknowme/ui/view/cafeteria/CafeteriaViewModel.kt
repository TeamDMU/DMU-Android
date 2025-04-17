package com.dongyang.android.youdongknowme.ui.view.cafeteria

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.data.remote.entity.Cafeteria
import com.dongyang.android.youdongknowme.data.repository.CafeteriaRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.standard.util.Weekdays
import com.dongyang.android.youdongknowme.ui.view.util.Event
import com.dongyang.android.youdongknowme.data.model.AnotherMenuItem
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.time.LocalDate

class CafeteriaViewModel(
    private val cafeteriaRepository: CafeteriaRepository,
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

    private val _koreanMenus: MutableLiveData<List<String>> = MutableLiveData()
    val koreanMenus: LiveData<List<String>> = _koreanMenus

    private val _anotherMenus: MutableLiveData<List<AnotherMenuItem>> = MutableLiveData()
    val anotherMenus: LiveData<List<AnotherMenuItem>> = _anotherMenus

    private val _selectedCategory = MutableLiveData<String>()
    val selectedCategory: LiveData<String> get() = _selectedCategory

    init {
        fetchCafeteria()
    }

    private fun fetchCafeteria() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            when (val result = cafeteriaRepository.fetchMenuList()) {
                is NetworkResult.Success -> {
                    val menuList = result.data
                    _cafeteriaList.value = menuList
                    _selectedDate.value = LocalDate.now()
                    _koreanMenus.value =
                        menuList.find { it.date == _selectedDate.value?.toString() }?.menus
                            ?: emptyList()
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

    fun updateMenuList(selectedDate: LocalDate) {
        val cafeteriaList = _cafeteriaList.value ?: emptyList()
        _selectedDate.value = selectedDate
        _koreanMenus.value = cafeteriaList.find { it.date == selectedDate.toString() }?.menus ?: emptyList()
    }

    fun updateDaysMenu(selectedDate: LocalDate) {
        viewModelScope.launch {
            val dateToWeekday: Weekdays = Weekdays.from(selectedDate.dayOfWeek)
            runCatching {
                cafeteriaRepository.fetchDaysMenus(dateToWeekday)
            }.onSuccess { anotherMenus ->
                _anotherMenus.value = anotherMenus.map { menu ->
                    AnotherMenuItem(
                        menu.menuNameKr,
                        menu.name,
                        "${formattedPrice.format(menu.price)}원"
                    )
                }
            }.onFailure {
                _isError.value = true
            }
        }
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    companion object {
        private val formattedPrice = DecimalFormat("#,###")
    }
}
