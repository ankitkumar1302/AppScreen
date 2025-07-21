package com.example.appscreen.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.appscreen.data.model.Device
import com.example.appscreen.data.model.DeviceCategory
import com.example.appscreen.data.model.DeviceType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

interface DeviceRepository {
    fun getDevices(): Flow<List<Device>>
    fun getDeviceCategories(): Flow<List<DeviceCategory>>
    suspend fun toggleDevice(deviceId: String)
    suspend fun updateDevice(device: Device)
    suspend fun addDevice(device: Device)
    suspend fun deleteDevice(deviceId: String)
    fun getDeviceById(deviceId: String): Flow<Device?>
}

class DeviceRepositoryImpl : DeviceRepository {
    
    private val _devices = MutableStateFlow(generateSampleDevices())
    private val devices = _devices.asStateFlow()
    
    override fun getDevices(): Flow<List<Device>> = devices
    
    override fun getDeviceCategories(): Flow<List<DeviceCategory>> {
        return _devices.map { deviceList ->
            listOf(
                DeviceCategory("Living Room", deviceList.filter { it.room == "Living Room" }),
                DeviceCategory("Bedroom", deviceList.filter { it.room == "Bedroom" }),
                DeviceCategory("Kitchen", deviceList.filter { it.room == "Kitchen" }),
                DeviceCategory("Bathroom", deviceList.filter { it.room == "Bathroom" })
            )
        }
    }
    
    override suspend fun toggleDevice(deviceId: String) {
        _devices.value = _devices.value.map { device ->
            if (device.id == deviceId) {
                device.copy(isOn = !device.isOn)
            } else {
                device
            }
        }
    }
    
    override suspend fun updateDevice(device: Device) {
        _devices.value = _devices.value.map { 
            if (it.id == device.id) device else it
        }
    }
    
    override suspend fun addDevice(device: Device) {
        _devices.value = _devices.value + device
    }
    
    override suspend fun deleteDevice(deviceId: String) {
        _devices.value = _devices.value.filter { it.id != deviceId }
    }
    
    override fun getDeviceById(deviceId: String): Flow<Device?> {
        return _devices.map { deviceList ->
            deviceList.find { it.id == deviceId }
        }
    }
    
    private fun generateSampleDevices(): List<Device> = listOf(
        Device("1", "Living Room Light", DeviceType.LIGHT, true, Icons.Filled.LightMode, "Living Room", 60f),
        Device("2", "Smart TV", DeviceType.TV, false, Icons.Filled.Tv, "Living Room", 150f),
        Device("3", "Air Conditioner", DeviceType.AC, true, Icons.Filled.AcUnit, "Living Room", 2000f),
        Device("4", "Smart Speaker", DeviceType.SPEAKER, true, Icons.Filled.Speaker, "Living Room", 30f),
        
        Device("5", "Bedroom Light", DeviceType.LIGHT, false, Icons.Filled.LightMode, "Bedroom", 40f),
        Device("6", "Ceiling Fan", DeviceType.FAN, true, Icons.Filled.Air, "Bedroom", 75f),
        Device("7", "Smart Lock", DeviceType.LOCK, true, Icons.Filled.Lock, "Bedroom", 10f),
        
        Device("8", "Kitchen Light", DeviceType.LIGHT, true, Icons.Filled.LightMode, "Kitchen", 60f),
        Device("9", "Refrigerator", DeviceType.REFRIGERATOR, true, Icons.Filled.Kitchen, "Kitchen", 150f),
        Device("10", "Oven", DeviceType.OVEN, false, Icons.Filled.Countertops, "Kitchen", 3000f),
        Device("11", "Coffee Maker", DeviceType.PLUG, false, Icons.Filled.Coffee, "Kitchen", 1000f),
        
        Device("12", "Bathroom Light", DeviceType.LIGHT, false, Icons.Filled.LightMode, "Bathroom", 40f),
        Device("13", "Water Heater", DeviceType.PLUG, true, Icons.Filled.HotTub, "Bathroom", 2500f)
    )
}