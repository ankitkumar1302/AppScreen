package com.example.appscreen.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.appscreen.ui.theme.HeaderSubTextColor
import com.example.appscreen.ui.theme.White
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    onMenuClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = "Hey, User!",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                )
                Text(
                    text = getCurrentDate(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 15.sp,
                        color = HeaderSubTextColor
                    )
                )
            }
        },
        actions = {
            IconButton(onClick = onNotificationClick) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Notifications",
                    tint = White
                )
            }
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Filled.GridView,
                    contentDescription = "Menu",
                    tint = White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = White,
            actionIconContentColor = White
        )
    )
}

private fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.getDefault())
    return dateFormat.format(Date())
}