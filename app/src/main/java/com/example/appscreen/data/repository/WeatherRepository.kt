package com.example.appscreen.data.repository

import com.example.appscreen.data.model.Weather
import com.example.appscreen.data.model.WeatherCondition
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

interface WeatherRepository {
    fun getWeather(): Flow<Weather>
    suspend fun refreshWeather()
}

@Singleton
class WeatherRepositoryImpl @Inject constructor() : WeatherRepository {
    
    private var currentWeather = Weather(
        temperature = 25,
        condition = WeatherCondition.PARTLY_CLOUDY,
        humidity = 65,
        windSpeed = 12.5f
    )
    
    override fun getWeather(): Flow<Weather> = flow {
        while (true) {
            emit(currentWeather)
            delay(60000) // Update every minute
        }
    }
    
    override suspend fun refreshWeather() {
        // Simulate API call
        delay(1000)
        currentWeather = currentWeather.copy(
            temperature = (20..30).random(),
            humidity = (50..80).random(),
            windSpeed = (5..20).random().toFloat(),
            lastUpdated = System.currentTimeMillis()
        )
    }
}