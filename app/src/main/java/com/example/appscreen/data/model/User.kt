package com.example.appscreen.data.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val profilePictureUrl: String? = null,
    val preferences: UserPreferences = UserPreferences()
)

data class UserPreferences(
    val notificationsEnabled: Boolean = true,
    val darkModeEnabled: Boolean = true,
    val temperatureUnit: String = "°C",
    val language: String = "en",
    val autoModeEnabled: Boolean = false
)