package com.example.appscreen.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appscreen.ui.theme.*

@Composable
fun SettingsScreen(navController: NavController) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(true) }
    var autoModeEnabled by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineSmall,
            color = White,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // General Settings
        SettingsSection(title = "General") {
            SettingItem(
                title = "Enable Notifications",
                icon = Icons.Filled.Notifications,
                checked = notificationsEnabled
            ) {
                notificationsEnabled = it
            }
            
            SettingItem(
                title = "Dark Mode",
                icon = Icons.Filled.Brightness4,
                checked = darkModeEnabled
            ) {
                darkModeEnabled = it
            }
            
            SettingItem(
                title = "Auto Mode",
                subtitle = "Automatically control devices",
                icon = Icons.Filled.AutoMode,
                checked = autoModeEnabled
            ) {
                autoModeEnabled = it
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Account Settings
        SettingsSection(title = "Account") {
            SettingItem(
                title = "Account Settings",
                icon = Icons.Filled.AccountCircle,
                isSwitchVisible = false,
                onClick = { /* TODO: Navigate to account */ }
            )
            
            SettingItem(
                title = "Privacy & Security",
                icon = Icons.Filled.Security,
                isSwitchVisible = false,
                onClick = { /* TODO: Navigate to privacy */ }
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // About
        SettingsSection(title = "About") {
            SettingItem(
                title = "Version",
                subtitle = "1.0.0",
                icon = Icons.Filled.Info,
                isSwitchVisible = false,
                onClick = { }
            )
            
            SettingItem(
                title = "Help & Support",
                icon = Icons.Filled.Help,
                isSwitchVisible = false,
                onClick = { /* TODO: Navigate to help */ }
            )
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = SubtleCyan,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Card(
            colors = CardDefaults.cardColors(containerColor = DeepGrey),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
private fun SettingItem(
    title: String,
    subtitle: String? = null,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    checked: Boolean = false,
    isSwitchVisible: Boolean = true,
    onClick: (() -> Unit)? = null,
    onCheckedChange: ((Boolean) -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = White
                )
                subtitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = GreyishTone
                    )
                }
            }
        }
        
        if (isSwitchVisible && onCheckedChange != null) {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = DarkBlue,
                    checkedTrackColor = SubtleCyan,
                    uncheckedThumbColor = GreyishTone,
                    uncheckedTrackColor = DeepGrey.copy(alpha = 0.7f)
                )
            )
        } else if (!isSwitchVisible) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Navigate",
                tint = GreyishTone
            )
        }
    }
}