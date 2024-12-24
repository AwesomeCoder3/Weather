package com.example.weatherapp.data

import com.example.weatherapp.data.apiresult.APIResult
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService{

    @GET("current.json")
    suspend fun getWeather(
        @Query("q") searchTerm : String,
        @Query("aqi")  aqi: String = "no" ,
        @Query("key") key : String = "85ec5ce7813d4970a1d12854242312"
    ): APIResult<WeatherObject>

}