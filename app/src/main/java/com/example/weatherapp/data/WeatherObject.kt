package com.example.weatherapp.data

data class WeatherObject(
    var location: Location? = null,
    var current: Current? = null
)

data class Condition (
    var text: String? = null,
    var icon: String? = null,
    var code: Int = 0
)


data class Current (
    var last_updated_epoch: Int = 0,
    var last_updated: String? = null,
    var temp_c: Double = 0.0,
    var temp_f: Double = 0.0,
    var is_day: Int = 0,
    var condition: Condition? = null,
    var wind_mph: Double = 0.0,
    var wind_kph: Double = 0.0,
    var wind_degree: Int = 0,
    var wind_dir: String? = null,
    var pressure_mb: Double = 0.0,
    var pressure_in: Double = 0.0,
    var precip_mm: Double = 0.0,
    var precip_in: Double = 0.0,
    var humidity: Int = 0,
    var cloud: Int = 0,
    var feelslike_c: Double = 0.0,
    var feelslike_f: Double = 0.0,
    var windchill_c: Double = 0.0,
    var windchill_f: Double = 0.0,
    var heatindex_c: Double = 0.0,
    var heatindex_f: Double = 0.0,
    var dewpoint_c: Double = 0.0,
    var dewpoint_f: Double = 0.0,
    var vis_km: Double = 0.0,
    var vis_miles: Double = 0.0,
    var uv: Double = 0.0,
    var gust_mph: Double = 0.0,
    var gust_kph: Double = 0.0
)


data class Location (
    var name: String? = null,
    var region: String? = null,
    var country: String? = null,
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    var tz_id: String? = null,
    var localtime_epoch: Int = 0,
    var localtime: String? = null,
)
