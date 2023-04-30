package com.kong.currencyapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.apilayer.com"
    val interceptor = HttpLoggingInterceptor()
    val httpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

    private val retrofit: Retrofit by lazy {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService: CurrencyService = retrofit.create(CurrencyService::class.java)
}