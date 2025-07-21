package com.example.appscreen.data.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Device(
    val id: String,
    val name: String,
    val type: DeviceType,
    val isOn: Boolean = false,
    val icon: ImageVector? = null,
    val room: String = "Living Room",
    val powerUsage: Float = 0f, // in watts
    val lastActiveTime: Long = System.currentTimeMillis()
)

enum class DeviceType {
    LIGHT,
    THERMOSTAT,
    CAMERA,
    SPEAKER,
    TV,
    LOCK,
    PLUG,
    FAN,
    AC,
    WASHING_MACHINE,
    REFRIGERATOR,
    OVEN
}

data class DeviceCategory(
    val name: String,
    val devices: List<Device>
)