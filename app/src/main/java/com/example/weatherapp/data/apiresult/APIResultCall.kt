package com.example.weatherapp.data.apiresult

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class APIResultCall<T>(
    private val callDelegate: Call<T>
) : Call<APIResult<T>> {


    override fun enqueue(callback: Callback<APIResult<T>>) = callDelegate.enqueue(
        object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
response.body()?.let {
    when(response.code()){
        in 200 .. 208 -> {
            callback.onResponse(this@APIResultCall, Response.success(APIResult.Success(it)))
        }
        in 400 .. 409 -> { code :Int ->
            callback.onResponse(this@APIResultCall, Response.success(
                APIResult.ApiError(
                    response.message(),
                    response.code()
                )
            ),
                )
        }

        else -> {}
    }

} ?: response.errorBody()?.let {
    callback.onResponse(
        this@APIResultCall,Response.success(
            APIResult.ApiError(
                "${response.message()} : ${it.string()}", response.code()
            )
        )
    )
}  ?: callback.onResponse(this@APIResultCall, Response.success(APIResult.UnknownError(null))) }

            override fun onFailure(call: Call<T>, t: Throwable) {

                callback.onResponse(this@APIResultCall, Response.success(APIResult.NetworkError(t)))
            }

        }
    )

    override fun isExecuted() = callDelegate.isExecuted

    override fun clone() = APIResultCall(callDelegate.clone())

    override fun isCanceled() = callDelegate.isCanceled

    override fun cancel() = callDelegate.cancel()

    override fun execute(): Response<APIResult<T>> {
        throw UnsupportedOperationException("APIResultCall doesn't support execute")
    }

    override fun request(): Request = callDelegate.request()
    override fun timeout(): Timeout {
        throw UnsupportedOperationException("APIResultCall doesn't support timeout")
    }
}