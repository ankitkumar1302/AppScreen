package com.example.appscreen.di

import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.appscreen.data.repository.DeviceRepository
import com.example.appscreen.data.repository.DeviceRepositoryImpl
import com.example.appscreen.data.repository.UserRepository
import com.example.appscreen.data.repository.UserRepositoryImpl
import com.example.appscreen.data.repository.WeatherRepository
import com.example.appscreen.data.repository.WeatherRepositoryImpl
import com.example.appscreen.presentation.viewmodel.HomeViewModel

val appModule = module {
    single<DeviceRepository> { DeviceRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }
    single<WeatherRepository> { WeatherRepositoryImpl() }
    // Register HomeViewModel for Compose & Koin DI
    viewModel { HomeViewModel(get(), get()) }
}