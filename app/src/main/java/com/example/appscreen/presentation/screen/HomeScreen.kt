package com.example.appscreen.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.appscreen.data.model.Device
import com.example.appscreen.data.model.Weather
import com.example.appscreen.data.model.WeatherCondition
import com.example.appscreen.navigation.Screen
import com.example.appscreen.presentation.component.DeviceCard
import com.example.appscreen.presentation.viewmodel.HomeViewModel
import com.example.appscreen.ui.theme.*
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = getViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        // Weather Card
        item {
            WeatherCard(
                weather = uiState.weather,
                onRefresh = { viewModel.refreshWeather() }
            )
        }
        
        // Quick Actions
        item {
            QuickActionsSection(navController)
        }
        
        // Device Categories
        item {
            DeviceCategoriesSection(
                categories = listOf("All", "Living Room", "Bedroom", "Kitchen", "Bathroom"),
                selectedCategory = uiState.selectedCategory,
                onCategorySelect = viewModel::selectCategory
            )
        }
        
        // Devices Grid
        item {
            val filteredDevices = if (uiState.selectedCategory == "All") {
                uiState.devices
            } else {
                uiState.devices.filter { it.room == uiState.selectedCategory }
            }
            
            DevicesGridSection(
                devices = filteredDevices,
                onDeviceToggle = viewModel::toggleDevice,
                onDeviceClick = { device ->
                    navController.navigate(Screen.DeviceDetail.createRoute(device.id))
                }
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun WeatherCard(
    weather: Weather?,
    onRefresh: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(SubtleCyan, LightCyan)
                    )
                )
                .clickable { onRefresh() }
                .padding(20.dp)
        ) {
            if (weather != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = weather.location,
                            style = MaterialTheme.typography.titleMedium,
                            color = DarkBlue
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${weather.temperature}${weather.temperatureUnit}",
                            style = MaterialTheme.typography.displayLarge.copy(
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            color = DarkBlue
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.WaterDrop,
                                contentDescription = "Humidity",
                                tint = DarkBlue,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${weather.humidity}%",
                                style = MaterialTheme.typography.bodyMedium,
                                color = DarkBlue
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Icon(
                                imageVector = Icons.Filled.Air,
                                contentDescription = "Wind",
                                tint = DarkBlue,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${weather.windSpeed} km/h",
                                style = MaterialTheme.typography.bodyMedium,
                                color = DarkBlue
                            )
                        }
                    }
                    
                    Icon(
                        imageVector = getWeatherIcon(weather.condition),
                        contentDescription = weather.condition.name,
                        tint = DarkBlue,
                        modifier = Modifier.size(80.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickActionsSection(navController: NavController) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            QuickActionCard(
                title = "Devices",
                icon = Icons.Filled.Devices,
                onClick = { navController.navigate(Screen.Devices.route) }
            )
        }
        item {
            QuickActionCard(
                title = "Energy",
                icon = Icons.Filled.ElectricBolt,
                onClick = { navController.navigate(Screen.Energy.route) }
            )
        }
        item {
            QuickActionCard(
                title = "Security",
                icon = Icons.Filled.Security,
                onClick = { navController.navigate(Screen.Security.route) }
            )
        }
        item {
            QuickActionCard(
                title = "Automation",
                icon = Icons.Filled.Schedule,
                onClick = { navController.navigate(Screen.Automation.route) }
            )
        }
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DeepGrey)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = SubtleCyan,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeviceCategoriesSection(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelect: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            FilterChip(
                selected = category == selectedCategory,
                onClick = { onCategorySelect(category) },
                label = { Text(category) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = SubtleCyan,
                    selectedLabelColor = DarkBlue,
                    containerColor = DeepGrey,
                    labelColor = GreyishTone
                )
            )
        }
    }
}

@Composable
private fun DevicesGridSection(
    devices: List<Device>,
    onDeviceToggle: (String) -> Unit,
    onDeviceClick: (Device) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.height(
            when {
                devices.isEmpty() -> 0.dp
                else -> {
                    val rows = (devices.size + 1) / 2 // Ceiling division for 2 columns
                    val itemHeight = 120.dp
                    val spacing = 12.dp
                    (rows * itemHeight + (rows - 1).coerceAtLeast(0) * spacing)
                }
            }
        )
    ) {
        items(devices) { device ->
            DeviceCard(
                device = device,
                onToggle = { onDeviceToggle(device.id) },
                onClick = { onDeviceClick(device) }
            )
        }
    }
}

private fun getWeatherIcon(condition: WeatherCondition): ImageVector {
    return when (condition) {
        WeatherCondition.SUNNY -> Icons.Filled.WbSunny
        WeatherCondition.PARTLY_CLOUDY -> Icons.Filled.Cloud
        WeatherCondition.CLOUDY -> Icons.Filled.CloudQueue
        WeatherCondition.RAINY -> Icons.Filled.WaterDrop
        WeatherCondition.STORMY -> Icons.Filled.Thunderstorm
        WeatherCondition.SNOWY -> Icons.Filled.AcUnit
        WeatherCondition.FOGGY -> Icons.Filled.Dehaze
    }
}