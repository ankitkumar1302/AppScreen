package com.example.appscreen.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavController
import com.example.appscreen.ui.theme.*

data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    val type: NotificationType,
    val isRead: Boolean = false
)

enum class NotificationType {
    DEVICE, SECURITY, ENERGY, SYSTEM, AUTOMATION
}

@Composable
fun NotificationsScreen(navController: NavController) {
    val notifications = remember {
        mutableStateListOf(
            Notification(
                "1",
                "Front door unlocked",
                "Front door was unlocked by John",
                "2 min ago",
                NotificationType.SECURITY,
                false
            ),
            Notification(
                "2",
                "High energy usage",
                "Your energy consumption is 20% higher than usual",
                "1 hour ago",
                NotificationType.ENERGY,
                false
            ),
            Notification(
                "3",
                "Good Morning automation completed",
                "All morning routines executed successfully",
                "3 hours ago",
                NotificationType.AUTOMATION,
                true
            ),
            Notification(
                "4",
                "Living room light offline",
                "Unable to connect to living room light",
                "Yesterday",
                NotificationType.DEVICE,
                true
            ),
            Notification(
                "5",
                "System update available",
                "New features and improvements are ready to install",
                "2 days ago",
                NotificationType.SYSTEM,
                true
            )
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
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
                text = "Notifications",
                style = MaterialTheme.typography.headlineSmall,
                color = White,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            TextButton(
                onClick = {
                    notifications.replaceAll { it.copy(isRead = true) }
                }
            ) {
                Text("Mark all read", color = SubtleCyan)
            }
        }
        
        // Notification settings
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SubtleCyan.copy(alpha = 0.2f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Info",
                    tint = SubtleCyan,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Smart notifications enabled",
                        style = MaterialTheme.typography.bodyMedium,
                        color = White
                    )
                    Text(
                        text = "Only important alerts will notify you",
                        style = MaterialTheme.typography.bodySmall,
                        color = GreyishTone
                    )
                }
            }
        }
        
        // Notifications list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(notifications) { notification ->
                NotificationItem(
                    notification = notification,
                    onRead = {
                        val index = notifications.indexOf(notification)
                        notifications[index] = notification.copy(isRead = true)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationItem(
    notification: Notification,
    onRead: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead) DeepGrey.copy(alpha = 0.6f) else DeepGrey
        ),
        onClick = onRead
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(getNotificationColor(notification.type).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getNotificationIcon(notification.type),
                    contentDescription = notification.type.name,
                    tint = getNotificationColor(notification.type),
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Content
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = notification.title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = if (notification.isRead) FontWeight.Normal else FontWeight.Bold
                        ),
                        color = White
                    )
                    if (!notification.isRead) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(SubtleCyan)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = GreyishTone
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = notification.time,
                    style = MaterialTheme.typography.bodySmall,
                    color = GreyishTone.copy(alpha = 0.7f)
                )
            }
        }
    }
}

private fun getNotificationIcon(type: NotificationType): androidx.compose.ui.graphics.vector.ImageVector {
    return when (type) {
        NotificationType.DEVICE -> Icons.Filled.Devices
        NotificationType.SECURITY -> Icons.Filled.Security
        NotificationType.ENERGY -> Icons.Filled.ElectricBolt
        NotificationType.SYSTEM -> Icons.Filled.SystemUpdate
        NotificationType.AUTOMATION -> Icons.Filled.Schedule
    }
}

private fun getNotificationColor(type: NotificationType): androidx.compose.ui.graphics.Color {
    return when (type) {
        NotificationType.DEVICE -> SubtleCyan
        NotificationType.SECURITY -> Color.Red
        NotificationType.ENERGY -> Color.Yellow
        NotificationType.SYSTEM -> GreyishTone
        NotificationType.AUTOMATION -> SubtleTeal
    }
}