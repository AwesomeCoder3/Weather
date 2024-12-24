package com.example.weatherapp.data.retrofit

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl


class AppCookieJar : CookieJar {
    private val cookieStore = mutableListOf<Cookie>()

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        TODO("Not yet implemented")
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        TODO("Not yet implemented")
    }


}