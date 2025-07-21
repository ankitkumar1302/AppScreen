package com.example.appscreen.presentation.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appscreen.ui.theme.*

@Composable
fun EnergyScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        // Energy Overview Card
        item {
            EnergyOverviewCard()
        }
        
        // Energy Usage Chart
        item {
            EnergyUsageChart()
        }
        
        // Device Energy Consumption
        item {
            DeviceEnergyConsumption()
        }
        
        // Energy Saving Tips
        item {
            EnergySavingTips()
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun EnergyOverviewCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DeepGrey)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Monthly Energy Usage",
                style = MaterialTheme.typography.titleMedium,
                color = White
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "487 kWh",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = SubtleCyan
                    )
                    Text(
                        text = "This month",
                        style = MaterialTheme.typography.bodySmall,
                        color = GreyishTone
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.TrendingDown,
                            contentDescription = "Trending Down",
                            tint = Color.Green,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "12%",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = Color.Green
                        )
                    }
                    Text(
                        text = "vs last month",
                        style = MaterialTheme.typography.bodySmall,
                        color = GreyishTone
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(
                progress = 0.65f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = SubtleCyan,
                trackColor = DeepGrey.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "65% of monthly budget (750 kWh)",
                style = MaterialTheme.typography.bodySmall,
                color = GreyishTone
            )
        }
    }
}

@Composable
private fun EnergyUsageChart() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DeepGrey)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Weekly Usage Pattern",
                style = MaterialTheme.typography.titleMedium,
                color = White
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                drawEnergyChart()
            }
        }
    }
}

private fun DrawScope.drawEnergyChart() {
    val chartWidth = size.width
    val chartHeight = size.height
    val barWidth = chartWidth / 14 // 7 days with spacing
    val values = listOf(0.6f, 0.8f, 0.7f, 0.9f, 0.65f, 0.75f, 0.5f)
    
    values.forEachIndexed { index, value ->
        val barHeight = chartHeight * value
        val x = index * 2 * barWidth + barWidth / 2
        
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(SubtleCyan, LightCyan)
            ),
            topLeft = Offset(x, chartHeight - barHeight),
            size = Size(barWidth, barHeight)
        )
    }
}

@Composable
private fun DeviceEnergyConsumption() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DeepGrey)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Top Energy Consumers",
                style = MaterialTheme.typography.titleMedium,
                color = White
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            listOf(
                Triple("Air Conditioner", 180f, Icons.Filled.AcUnit),
                Triple("Water Heater", 120f, Icons.Filled.HotTub),
                Triple("Oven", 85f, Icons.Filled.Countertops),
                Triple("Washing Machine", 60f, Icons.Filled.LocalLaundryService)
            ).forEach { (device, consumption, icon) ->
                DeviceEnergyItem(device, consumption, icon)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun DeviceEnergyItem(
    deviceName: String,
    consumption: Float,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = deviceName,
            tint = SubtleCyan,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = deviceName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = White
                )
                Text(
                    text = "${consumption.toInt()} kWh",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = SubtleCyan
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = consumption / 200f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = SubtleCyan,
                trackColor = DeepGrey.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
private fun EnergySavingTips() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SubtleTeal.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Lightbulb,
                    contentDescription = "Tips",
                    tint = SubtleTeal,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Energy Saving Tip",
                    style = MaterialTheme.typography.titleMedium,
                    color = White
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Your AC is consuming 37% of total energy. Consider setting it to 24°C for optimal efficiency.",
                style = MaterialTheme.typography.bodyMedium,
                color = GreyishTone
            )
        }
    }
}