package com.example.appscreen.data.repository

import com.example.appscreen.data.model.User
import com.example.appscreen.data.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface UserRepository {
    fun getUser(): Flow<User>
    suspend fun updateUserPreferences(preferences: UserPreferences)
    suspend fun logout()
}

class UserRepositoryImpl : UserRepository {
    
    private val _user = MutableStateFlow(
        User(
            id = "1",
            name = "John Doe",
            email = "john.doe@example.com",
            profilePictureUrl = null,
            preferences = UserPreferences()
        )
    )
    private val user = _user.asStateFlow()
    
    override fun getUser(): Flow<User> = user
    
    override suspend fun updateUserPreferences(preferences: UserPreferences) {
        _user.value = _user.value.copy(preferences = preferences)
    }
    
    override suspend fun logout() {
        // Clear user data
        // In a real app, this would clear tokens, cache, etc.
    }
}