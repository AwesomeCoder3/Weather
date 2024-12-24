package com.example.weatherapp.data.retrofit

import com.example.weatherapp.data.WeatherService
import com.example.weatherapp.data.apiresult.APIResultAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitProvider () : KoinComponent {
     private val client: OkHttpClient by inject()
     private val moshi: Moshi by inject()

     fun get(): Retrofit = Retrofit.Builder()
          .baseUrl("https://api.weatherapi.com/v1/")
          .client(client)
          .addConverterFactory(MoshiConverterFactory.create(moshi))
          .addCallAdapterFactory(APIResultAdapterFactory())
          .build()

     fun provideService(retrofit: Retrofit): WeatherService =
          retrofit.create(WeatherService::class.java)


}