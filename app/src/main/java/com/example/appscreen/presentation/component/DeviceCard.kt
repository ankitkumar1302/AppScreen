package com.example.appscreen.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appscreen.data.model.Device
import com.example.appscreen.ui.theme.*

@Composable
fun DeviceCard(
    device: Device,
    onToggle: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (device.isOn) DeepGrey else DeepGrey.copy(alpha = 0.7f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                device.icon?.let { icon ->
                    Icon(
                        imageVector = icon,
                        contentDescription = device.name,
                        tint = if (device.isOn) SubtleCyan else GreyishTone,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Switch(
                    checked = device.isOn,
                    onCheckedChange = { onToggle() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = DarkBlue,
                        checkedTrackColor = SubtleCyan,
                        uncheckedThumbColor = GreyishTone,
                        uncheckedTrackColor = DeepGrey.copy(alpha = 0.7f)
                    ),
                    modifier = Modifier.height(20.dp)
                )
            }
            
            Column {
                Text(
                    text = device.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = White
                )
                Text(
                    text = device.room,
                    style = MaterialTheme.typography.bodySmall,
                    color = GreyishTone
                )
            }
        }
    }
}