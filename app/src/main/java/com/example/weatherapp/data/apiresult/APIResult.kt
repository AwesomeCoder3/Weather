package com.example.weatherapp.data.apiresult

sealed interface APIResult<T>{
    /**
     * Success response with body
     */
    data class Success<T>(val body: T) : APIResult<T>

    /**
     * Failure response with body
     */
    data class ApiError<U>(val body: String, val code: Int) : APIResult<U>

    /**
     * Network error
     */
    data class NetworkError<T>(val error: Throwable) : APIResult<T>

    /**
     * Unknown Error,
     */
    data class UnknownError<T>(val error: Throwable?) : APIResult<T>

}
