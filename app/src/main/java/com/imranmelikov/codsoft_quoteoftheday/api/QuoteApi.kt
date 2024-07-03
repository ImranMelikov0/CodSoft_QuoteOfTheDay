package com.imranmelikov.codsoft_quoteoftheday.api

import retrofit2.Response
import retrofit2.http.GET

interface QuoteApi {
    @GET("advice")
    suspend fun getQuote(): Response<Quote>
}