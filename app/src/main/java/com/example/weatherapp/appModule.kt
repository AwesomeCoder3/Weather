package com.example.weatherapp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.weatherapp.data.retrofit.OkHttpProvider
import com.example.weatherapp.data.retrofit.RetrofitProvider
import com.example.weatherapp.data.WeatherDataStore
import com.example.weatherapp.data.WeatherRepo
import com.example.weatherapp.data.WeatherRepoImplementation
import com.example.weatherapp.presentation.MainViewModel
import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule= module{
    single { WeatherDataStore() }
    //create datastore factory
    factory (named(DATA_STORE_FACTORY)){
        (dataStoreName : String) ->
        PreferenceDataStoreFactory.create (
            produceFile = {
                androidApplication().preferencesDataStoreFile(dataStoreName)
            }
        )
    }
    single<WeatherRepo>{WeatherRepoImplementation()}
    single {RetrofitProvider()}
    single {OkHttpProvider()}
    single { get<RetrofitProvider>().get()}
    single { get<OkHttpProvider>().get()}
    single { get<DataStore<Preferences>>(qualifier = named(DATA_STORE_FACTORY),
        parameters = {parametersOf(WeatherDataStore.FILE_NAME)}
    )}
    single { get<RetrofitProvider>().provideService(get())}
    single { Moshi.Builder().build() }



    viewModel { MainViewModel() }



}

const val DATA_STORE_FACTORY = "data store factory"