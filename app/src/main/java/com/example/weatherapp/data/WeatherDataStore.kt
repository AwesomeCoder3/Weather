package com.example.weatherapp.data


import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class WeatherDataStore(
) : KoinComponent{
    private val dataStore: DataStore<Preferences> by inject()


    companion object {
        const val FILE_NAME = "weather"
    }

    suspend fun writeToDataStore(value: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(FILE_NAME)] = value
        }
    }

    suspend fun readFromDataStore(): Flow<String> {
       return dataStore.data.catch {
            if(it is IOException){
                emit(emptyPreferences())
            }
            else{
                throw it
            }
        }.map {
            it[stringPreferencesKey(FILE_NAME)] ?: ""
        }

    }




    suspend fun resetWeather(){
        dataStore.edit {
            it.clear()
        }
    }
}