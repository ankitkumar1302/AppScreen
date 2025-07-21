package com.example.appscreen.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.appscreen.ui.theme.GreyishTone
import com.example.appscreen.ui.theme.SubtleCyan

@Composable
fun BottomNavigationBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    BottomAppBar(
        containerColor = Color.Transparent,
        contentColor = GreyishTone
    ) {
        listOf(
            NavigationItem("Home", Icons.Filled.Home),
            NavigationItem("Stats", Icons.Filled.BarChart),
            NavigationItem("Settings", Icons.Filled.Settings),
            NavigationItem("Profile", Icons.Filled.Person)
        ).forEachIndexed { index, item ->
            val isSelected = selectedItem == index
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        onClick = { onItemSelected(index) },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = if (isSelected) SubtleCyan else GreyishTone,
                    modifier = Modifier.size(24.dp)
                )
                if (isSelected) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(RoundedCornerShape(50))
                            .background(SubtleCyan)
                    )
                }
            }
        }
    }
}

private data class NavigationItem(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)