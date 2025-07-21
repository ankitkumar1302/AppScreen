package com.example.appscreen.data.model

data class Weather(
    val temperature: Int,
    val temperatureUnit: String = "°C",
    val condition: WeatherCondition,
    val humidity: Int,
    val windSpeed: Float,
    val location: String = "Home",
    val lastUpdated: Long = System.currentTimeMillis()
)

enum class WeatherCondition {
    SUNNY,
    PARTLY_CLOUDY,
    CLOUDY,
    RAINY,
    STORMY,
    SNOWY,
    FOGGY
}