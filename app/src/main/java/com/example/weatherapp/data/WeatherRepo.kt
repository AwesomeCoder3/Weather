package com.example.weatherapp.data

import com.example.weatherapp.data.apiresult.APIResult

interface WeatherRepo{
    suspend fun fetchWeather(location : String) : Result<WeatherObject>
   suspend fun saveWeather(toString: String)
    suspend fun fetchWeather() : String
    suspend fun resetWeather()

}