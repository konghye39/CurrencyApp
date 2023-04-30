package com.kong.currencyapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kong.currencyapp.data.CurrencyResponse
import com.kong.currencyapp.data.Resource
import com.kong.currencyapp.repository.CurrencyRepository
import com.kong.currencyapp.network.RetrofitClient
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val repository = CurrencyRepository(RetrofitClient.apiService)
    private val _currencyData = MutableLiveData<Resource<CurrencyResponse>>()
    val currencyData: LiveData<Resource<CurrencyResponse>> = _currencyData
    val key = BuildConfig.api_key
    fun getCurrencyData(base: String, symbols: String) {
        viewModelScope.launch {
            _currencyData.value = Resource.loading()
            _currencyData.value = repository.getCurrencyData(key,base, symbols) }
        }
    }
