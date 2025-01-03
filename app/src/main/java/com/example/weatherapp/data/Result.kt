package com.example.weatherapp.data


sealed interface Result<out T> {
    data class Success<T>(val value : T) : Result<T>
    data class Failure(val throwable : Throwable? = null) : Result<Nothing>



}