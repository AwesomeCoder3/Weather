package com.example.weatherapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.Result
import com.example.weatherapp.data.WeatherObject
import com.example.weatherapp.data.WeatherRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : ViewModel(), KoinComponent {

    private val weatherRepo : WeatherRepo by inject()

    val stateFlow = MutableStateFlow(State())

    init {
        viewModelScope.launch {
            fetchLocalWeather()
        }
    }

    suspend fun fetchWeather(location : String){
        viewModelScope.launch {
           when(val result  = weatherRepo.fetchWeather(location)) {

                is Result.Success -> {
                  stateFlow.update{
                      it.copy(weather = result.value,screenState = WeatherScreenState.SEARCH)
                  }
                }

                is Result.Failure -> {
                    stateFlow.update{
                        it.copy(screenState = WeatherScreenState.SEARCH,weather = null)
                    }
                }
            }
        }
    }
    suspend fun fetchLocalWeather(){
        viewModelScope.launch {
            val location  = weatherRepo.fetchWeather()
            if(!location.isEmpty())
                when(val result  = weatherRepo.fetchWeather(location)) {

                is Result.Success -> {
                    stateFlow.update{
                        it.copy(weather = result.value,screenState = WeatherScreenState.HOME)
                    }
                }

                is Result.Failure -> {
                    stateFlow.update{
                        it.copy(screenState = WeatherScreenState.EMPTY,weather = null)
                    }
                }
            }
        }
    }

    suspend fun cardClicked() {
        viewModelScope.launch {
            weatherRepo.saveWeather(stateFlow.value.weather?.location?.name.toString())
            stateFlow.update{
                it.copy(screenState = WeatherScreenState.HOME,weather = stateFlow.value.weather)
            }
        }
    }


    data class State(
        val weather : WeatherObject? = null,
        val screenState : WeatherScreenState = WeatherScreenState.EMPTY,

        )

}
enum class  WeatherScreenState{
    EMPTY,
    HOME,
    SEARCH
}