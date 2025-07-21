package com.example.appscreen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appscreen.data.model.Device
import com.example.appscreen.data.model.DeviceCategory
import com.example.appscreen.data.model.Weather
import com.example.appscreen.data.repository.DeviceRepository
import com.example.appscreen.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val weather: Weather? = null,
    val devices: List<Device> = emptyList(),
    val deviceCategories: List<DeviceCategory> = emptyList(),
    val selectedCategory: String = "All",
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        loadData()
    }
    
    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            // Use coroutineScope to ensure all operations complete before setting isLoading to false
            try {
                // Launch all data collection jobs in parallel
                launch {
                    // Collect weather data
                    weatherRepository.getWeather()
                        .catch { e -> 
                            _uiState.update { it.copy(error = e.message) }
                        }
                        .collect { weather ->
                            _uiState.update { it.copy(weather = weather) }
                        }
                }
                
                launch {
                    // Collect devices
                    deviceRepository.getDevices()
                        .catch { e -> 
                            _uiState.update { it.copy(error = e.message) }
                        }
                        .collect { devices ->
                            _uiState.update { it.copy(devices = devices) }
                        }
                }
                
                launch {
                    // Collect device categories
                    deviceRepository.getDeviceCategories()
                        .catch { e -> 
                            _uiState.update { it.copy(error = e.message) }
                        }
                        .collect { categories ->
                            _uiState.update { it.copy(deviceCategories = categories) }
                        }
                }
            } finally {
                // Set loading to false only after all operations complete or fail
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
    
    fun toggleDevice(deviceId: String) {
        viewModelScope.launch {
            deviceRepository.toggleDevice(deviceId)
        }
    }
    
    fun selectCategory(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
    }
    
    fun refreshWeather() {
        viewModelScope.launch {
            weatherRepository.refreshWeather()
        }
    }
}