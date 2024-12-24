package com.example.weatherapp.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent

class OkHttpProvider: KoinComponent {

    fun get() : OkHttpClient{
        val defaultLogger = HttpLoggingInterceptor().apply { HttpLoggingInterceptor.Level.BODY }
        val builder = OkHttpClient.Builder()
            .addInterceptor(defaultLogger)
        return builder.build()
    }
}