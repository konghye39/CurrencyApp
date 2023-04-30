package com.kong.currencyapp.data

//응답 받아올 data class
data class CurrencyResponse(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val timestamp: Long,
    val source: String,
    val quotes: Map<String, Double>
)
