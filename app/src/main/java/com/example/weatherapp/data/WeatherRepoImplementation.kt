package com.example.weatherapp.data

import com.example.weatherapp.data.apiresult.APIResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WeatherRepoImplementation : WeatherRepo, KoinComponent {
    private val weatherService : WeatherService by inject()
    private val weatherDataStore : WeatherDataStore by inject()

    override suspend fun fetchWeather(location: String): Result<WeatherObject> {

        var result : Result<WeatherObject> = Result.Failure()
        withContext(Dispatchers.IO){
              when( val apiResult = weatherService.getWeather(location)){
                is APIResult.Success ->{
                  result =  Result.Success(apiResult.body)
                }

                is APIResult.ApiError ->result= Result.Failure()

                is APIResult.NetworkError ->{
                    //  Timber.e(apiResult.error)
                    result= Result.Failure()

                }

                is APIResult.UnknownError ->{
                    //  Timber.e(apiResult.error)
                    result=  Result.Failure()
                }
            }
        }
        return  result
    }

    override suspend fun fetchWeather() : String {
       return weatherDataStore.readFromDataStore().first()
    }

    override suspend fun saveWeather(weather: String) {
        weatherDataStore.writeToDataStore(weather)
    }

    override suspend fun resetWeather() {
      weatherDataStore.resetWeather()
    }
}