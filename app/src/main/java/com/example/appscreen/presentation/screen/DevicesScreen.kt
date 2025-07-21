package com.example.appscreen.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.appscreen.navigation.Screen
import com.example.appscreen.presentation.component.DeviceCard
import com.example.appscreen.presentation.viewmodel.HomeViewModel
import com.example.appscreen.ui.theme.DeepGrey
import com.example.appscreen.ui.theme.GreyishTone
import com.example.appscreen.ui.theme.SubtleCyan
import com.example.appscreen.ui.theme.White
import org.koin.androidx.compose.getViewModel

@Composable
fun DevicesScreen(
    navController: NavController,
    viewModel: HomeViewModel = getViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "All Devices",
                style = MaterialTheme.typography.headlineSmall,
                color = White,
                fontWeight = FontWeight.Bold
            )

            IconButton(
                onClick = { /* TODO: Add device */ }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Device",
                    tint = SubtleCyan
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Device Summary
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DeepGrey)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DeviceSummaryItem(
                    count = uiState.devices.count { it.isOn },
                    label = "Active"
                )
                DeviceSummaryItem(
                    count = uiState.devices.size,
                    label = "Total"
                )
                DeviceSummaryItem(
                    count = uiState.deviceCategories.size,
                    label = "Rooms"
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Device List by Room
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            uiState.deviceCategories.forEach { category ->
                item {
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = SubtleCyan,
                        fontWeight = FontWeight.Medium
                    )
                }

                items(category.devices.chunked(2)) { devicePair ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        devicePair.forEach { device ->
                            Box(modifier = Modifier.weight(1f)) {
                                DeviceCard(
                                    device = device,
                                    onToggle = { viewModel.toggleDevice(device.id) },
                                    onClick = {
                                        navController.navigate(
                                            Screen.DeviceDetail.createRoute(device.id)
                                        )
                                    }
                                )
                            }
                        }
                        if (devicePair.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun DeviceSummaryItem(
    count: Int,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = SubtleCyan
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = GreyishTone
        )
    }
}