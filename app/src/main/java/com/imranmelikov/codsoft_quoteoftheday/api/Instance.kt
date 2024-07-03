package com.imranmelikov.codsoft_quoteoftheday.api

import com.imranmelikov.codsoft_quoteoftheday.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Instance {
    val api=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(QuoteApi::class.java)
}