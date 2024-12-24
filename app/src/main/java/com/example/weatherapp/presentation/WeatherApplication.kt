package com.example.weatherapp.presentation

import android.app.Application
import com.example.weatherapp.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext


class WeatherApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin(){
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@WeatherApplication)
            modules(modules = appModule)
        }
    }

}