package com.example.appscreen.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appscreen.ui.theme.*

@Composable
fun SecurityScreen() {
    var isArmed by remember { mutableStateOf(false) }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        // Security Status
        item {
            SecurityStatusCard(isArmed = isArmed) {
                isArmed = !isArmed
            }
        }
        
        // Security Cameras
        item {
            Text(
                text = "Security Cameras",
                style = MaterialTheme.typography.titleLarge,
                color = White,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CameraCard(
                    name = "Front Door",
                    isOnline = true,
                    modifier = Modifier.weight(1f)
                )
                CameraCard(
                    name = "Backyard",
                    isOnline = true,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CameraCard(
                    name = "Garage",
                    isOnline = false,
                    modifier = Modifier.weight(1f)
                )
                CameraCard(
                    name = "Living Room",
                    isOnline = true,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        // Recent Activity
        item {
            Text(
                text = "Recent Activity",
                style = MaterialTheme.typography.titleLarge,
                color = White,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DeepGrey)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    ActivityItem(
                        time = "2 minutes ago",
                        event = "Front door opened",
                        icon = Icons.Filled.DoorFront
                    )
                    Divider(
                        color = DeepGrey.copy(alpha = 0.5f),
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                    ActivityItem(
                        time = "15 minutes ago",
                        event = "Motion detected - Backyard",
                        icon = Icons.Filled.Sensors
                    )
                    Divider(
                        color = DeepGrey.copy(alpha = 0.5f),
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                    ActivityItem(
                        time = "1 hour ago",
                        event = "System armed",
                        icon = Icons.Filled.Security
                    )
                }
            }
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SecurityStatusCard(
    isArmed: Boolean,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isArmed) SubtleCyan.copy(alpha = 0.2f) else DeepGrey
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(
                        if (isArmed) SubtleCyan else GreyishTone.copy(alpha = 0.3f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isArmed) Icons.Filled.Security else Icons.Filled.SecurityUpdateGood,
                    contentDescription = "Security Status",
                    tint = if (isArmed) DarkBlue else GreyishTone,
                    modifier = Modifier.size(60.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = if (isArmed) "System Armed" else "System Disarmed",
                style = MaterialTheme.typography.headlineMedium,
                color = White,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = if (isArmed) "All sensors active" else "Tap to arm system",
                style = MaterialTheme.typography.bodyMedium,
                color = GreyishTone
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = onToggle,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isArmed) Color.Red.copy(alpha = 0.8f) else SubtleCyan
                )
            ) {
                Text(
                    text = if (isArmed) "DISARM" else "ARM SYSTEM",
                    color = White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun CameraCard(
    name: String,
    isOnline: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(150.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DeepGrey)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Camera preview placeholder
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Videocam,
                    contentDescription = "Camera",
                    tint = GreyishTone,
                    modifier = Modifier.size(48.dp)
                )
            }
            
            // Camera info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(
                        DeepGrey.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = White,
                    fontWeight = FontWeight.Medium
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (isOnline) Color.Green else Color.Red)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isOnline) "Online" else "Offline",
                        style = MaterialTheme.typography.bodySmall,
                        color = GreyishTone
                    )
                }
            }
        }
    }
}

@Composable
private fun ActivityItem(
    time: String,
    event: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(SubtleCyan.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = event,
                tint = SubtleCyan,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = event,
                style = MaterialTheme.typography.bodyLarge,
                color = White
            )
            Text(
                text = time,
                style = MaterialTheme.typography.bodySmall,
                color = GreyishTone
            )
        }
    }
}