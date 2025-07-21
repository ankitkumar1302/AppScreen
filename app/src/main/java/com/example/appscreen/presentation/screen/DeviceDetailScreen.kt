package com.example.appscreen.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appscreen.ui.theme.*

@Composable
fun DeviceDetailScreen(
    navController: NavController,
    deviceId: String
) {
    // TODO: Get device from ViewModel
    var isDeviceOn by remember { mutableStateOf(true) }
    var brightness by remember { mutableStateOf(0.8f) }
    var temperature by remember { mutableStateOf(22f) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back button and title
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = White
                )
            }
            
            Text(
                text = "Living Room Light",
                style = MaterialTheme.typography.headlineSmall,
                color = White,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Device Status Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DeepGrey)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.LightMode,
                    contentDescription = "Device",
                    tint = if (isDeviceOn) SubtleCyan else GreyishTone,
                    modifier = Modifier.size(80.dp)
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Switch(
                    checked = isDeviceOn,
                    onCheckedChange = { isDeviceOn = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = DarkBlue,
                        checkedTrackColor = SubtleCyan,
                        uncheckedThumbColor = GreyishTone,
                        uncheckedTrackColor = DeepGrey.copy(alpha = 0.7f)
                    ),
                    modifier = Modifier.scale(1.5f)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = if (isDeviceOn) "ON" else "OFF",
                    style = MaterialTheme.typography.headlineMedium,
                    color = if (isDeviceOn) SubtleCyan else GreyishTone,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Controls
        if (isDeviceOn) {
            // Brightness Control
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DeepGrey)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Brightness",
                            style = MaterialTheme.typography.titleMedium,
                            color = White
                        )
                        Text(
                            text = "${(brightness * 100).toInt()}%",
                            style = MaterialTheme.typography.bodyLarge,
                            color = SubtleCyan
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Slider(
                        value = brightness,
                        onValueChange = { brightness = it },
                        colors = SliderDefaults.colors(
                            thumbColor = SubtleCyan,
                            activeTrackColor = SubtleCyan,
                            inactiveTrackColor = DeepGrey.copy(alpha = 0.5f)
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Schedule Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DeepGrey)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Schedule,
                            contentDescription = "Schedule",
                            tint = White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Schedule",
                                style = MaterialTheme.typography.titleMedium,
                                color = White
                            )
                            Text(
                                text = "Turn off at 11:00 PM",
                                style = MaterialTheme.typography.bodySmall,
                                color = GreyishTone
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Filled.ChevronRight,
                        contentDescription = "Configure",
                        tint = GreyishTone
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Device Info
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DeepGrey.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                DeviceInfoItem("Room", "Living Room")
                Spacer(modifier = Modifier.height(12.dp))
                DeviceInfoItem("Type", "Smart Light")
                Spacer(modifier = Modifier.height(12.dp))
                DeviceInfoItem("Model", "Philips Hue E27")
                Spacer(modifier = Modifier.height(12.dp))
                DeviceInfoItem("Power Usage", "9W")
            }
        }
    }
}

@Composable
private fun DeviceInfoItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = GreyishTone
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = White
        )
    }
}