package com.example.appscreen

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.example.appscreen.di.appModule // Adjust if your module is elsewhere

class SmartHomeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SmartHomeApp)
            modules(appModule)
        }
    }
}