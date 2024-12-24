package com.example.weatherapp.data.apiresult

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class APIResultAdapter<T>(
    private val successType: Type,
) : CallAdapter<T, Call<APIResult<T>>> {
    override fun responseType(): Type = successType
    override fun adapt(call: Call<T>) : Call<APIResult<T>> = APIResultCall(call)
}