package com.example.appscreen.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appscreen.ui.theme.*

@Composable
fun ProfileScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        // Profile Picture
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(GreyishTone),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "User Avatar",
                tint = White,
                modifier = Modifier.size(70.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // User Info
        Text(
            text = "John Doe",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = White,
                fontWeight = FontWeight.Bold
            )
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "john.doe@example.com",
            style = MaterialTheme.typography.bodyLarge,
            color = GreyishTone
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Profile Stats
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProfileStatItem(value = "13", label = "Devices")
            ProfileStatItem(value = "4", label = "Rooms")
            ProfileStatItem(value = "7", label = "Automations")
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Profile Actions
        ProfileActionItem(
            icon = Icons.Filled.Edit,
            title = "Edit Profile",
            onClick = { /* TODO */ }
        )
        
        ProfileActionItem(
            icon = Icons.Filled.Notifications,
            title = "Notification Settings",
            onClick = { navController.navigate("notifications") }
        )
        
        ProfileActionItem(
            icon = Icons.Filled.Security,
            title = "Privacy & Security",
            onClick = { /* TODO */ }
        )
        
        ProfileActionItem(
            icon = Icons.Filled.Help,
            title = "Help & Support",
            onClick = { /* TODO */ }
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Logout Button
        Button(
            onClick = { /* TODO: Handle logout */ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SubtleCyan)
        ) {
            Text(
                text = "Log Out",
                color = DarkBlue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun ProfileStatItem(value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = SubtleCyan
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = GreyishTone
        )
    }
}

@Composable
private fun ProfileActionItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DeepGrey)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = White
                )
            }
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Navigate",
                tint = GreyishTone
            )
        }
    }
}