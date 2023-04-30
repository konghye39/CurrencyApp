package com.kong.currencyapp.repository

import com.kong.currencyapp.data.CurrencyResponse
import com.kong.currencyapp.data.Resource
import com.kong.currencyapp.network.CurrencyService

class CurrencyRepository(private val apiService: CurrencyService) {
    suspend fun getCurrencyData(
        apiKey: String,
        base: String,
        symbols: String
    ): Resource<CurrencyResponse> {
        return try {
            val response = apiService.getCurrencyData(apiKey, base, symbols)
            if (response.isSuccessful) {
                Resource.success(response.body())
            } else {
                Resource.error(response.message())
            }
        } catch (e: Exception) {
            Resource.error(e.message ?: "Unknown error occurred")
        }
    }
}