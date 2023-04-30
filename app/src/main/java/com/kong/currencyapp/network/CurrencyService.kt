package com.kong.currencyapp.network


import com.kong.currencyapp.data.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface CurrencyService {
    @GET("/currency_data/live")
    suspend fun getCurrencyData(
        @Header("apikey") apikey: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): Response<CurrencyResponse>

}