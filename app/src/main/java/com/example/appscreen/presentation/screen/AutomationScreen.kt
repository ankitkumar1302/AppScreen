package com.example.appscreen.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

data class AutomationRule(
    val id: String,
    val name: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val isActive: Boolean,
    val trigger: String,
    val actions: List<String>
)

@Composable
fun AutomationScreen(navController: NavController) {
    var showCreateDialog by remember { mutableStateOf(false) }
    
    val automationRules = remember {
        mutableStateListOf(
            AutomationRule(
                "1",
                "Good Morning",
                "Turns on lights and adjusts temperature",
                Icons.Filled.WbSunny,
                true,
                "7:00 AM",
                listOf("Turn on bedroom lights", "Set temperature to 22°C", "Open blinds")
            ),
            AutomationRule(
                "2",
                "Leave Home",
                "Secures home when you leave",
                Icons.Filled.ExitToApp,
                true,
                "When last person leaves",
                listOf("Turn off all lights", "Lock doors", "Arm security system")
            ),
            AutomationRule(
                "3",
                "Movie Time",
                "Sets the perfect movie ambiance",
                Icons.Filled.Movie,
                false,
                "Manual trigger",
                listOf("Dim living room lights", "Turn on TV", "Close blinds")
            ),
            AutomationRule(
                "4",
                "Good Night",
                "Prepares home for sleep",
                Icons.Filled.Bedtime,
                true,
                "10:30 PM",
                listOf("Turn off lights", "Lock doors", "Set temperature to 20°C")
            )
        )
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            // Header Section
            item {
                AutomationHeader()
            }
            
            // Quick Actions
            item {
                QuickAutomationActions()
            }
            
            // Automation Rules
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Automation Rules",
                        style = MaterialTheme.typography.titleLarge,
                        color = White,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = { showCreateDialog = true }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add Rule",
                            tint = SubtleCyan
                        )
                    }
                }
            }
            
            items(automationRules) { rule ->
                AutomationRuleCard(
                    rule = rule,
                    onToggle = { 
                        val index = automationRules.indexOf(rule)
                        automationRules[index] = rule.copy(isActive = !rule.isActive)
                    }
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
    
    if (showCreateDialog) {
        CreateAutomationDialog(
            onDismiss = { showCreateDialog = false },
            onCreate = { /* TODO: Create automation */ }
        )
    }
}

@Composable
private fun AutomationHeader() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DeepGrey)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Smart Automation",
                style = MaterialTheme.typography.headlineSmall,
                color = White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Automate your home with intelligent rules and schedules",
                style = MaterialTheme.typography.bodyMedium,
                color = GreyishTone
            )
        }
    }
}

@Composable
private fun QuickAutomationActions() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(Modifier.weight(1f)) {
            QuickActionChip(
                text = "Scenes",
                icon = Icons.Filled.Dashboard,
                onClick = { /* TODO */ }
            )
        }
        Box(Modifier.weight(1f)) {
            QuickActionChip(
                text = "Schedules",
                icon = Icons.Filled.Schedule,
                onClick = { /* TODO */ }
            )
        }
        Box(Modifier.weight(1f)) {
            QuickActionChip(
                text = "Triggers",
                icon = Icons.Filled.FlashOn,
                onClick = { /* TODO */ }
            )
        }
    }
}

@Composable
private fun QuickActionChip(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SubtleCyan.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = SubtleCyan,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = White
            )
        }
    }
}

@Composable
private fun AutomationRuleCard(
    rule: AutomationRule,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (rule.isActive) DeepGrey else DeepGrey.copy(alpha = 0.6f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = rule.icon,
                        contentDescription = rule.name,
                        tint = if (rule.isActive) SubtleCyan else GreyishTone,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = rule.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = White,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = rule.trigger,
                            style = MaterialTheme.typography.bodySmall,
                            color = SubtleCyan
                        )
                    }
                }
                
                Switch(
                    checked = rule.isActive,
                    onCheckedChange = { onToggle() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = DarkBlue,
                        checkedTrackColor = SubtleCyan,
                        uncheckedThumbColor = GreyishTone,
                        uncheckedTrackColor = DeepGrey.copy(alpha = 0.7f)
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = rule.description,
                style = MaterialTheme.typography.bodyMedium,
                color = GreyishTone
            )
            
            if (rule.actions.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rule.actions.take(2).forEach { action ->
                        AssistChip(
                            onClick = { },
                            label = { 
                                Text(
                                    text = action,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = DeepGrey.copy(alpha = 0.5f),
                                labelColor = GreyishTone
                            )
                        )
                    }
                    if (rule.actions.size > 2) {
                        AssistChip(
                            onClick = { },
                            label = { 
                                Text(
                                    text = "+${rule.actions.size - 2} more",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = DeepGrey.copy(alpha = 0.5f),
                                labelColor = SubtleCyan
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CreateAutomationDialog(
    onDismiss: () -> Unit,
    onCreate: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Create Automation",
                style = MaterialTheme.typography.headlineSmall,
                color = White
            )
        },
        text = {
            Column {
                Text(
                    text = "Choose automation type:",
                    style = MaterialTheme.typography.bodyLarge,
                    color = GreyishTone
                )
                Spacer(modifier = Modifier.height(16.dp))
                // TODO: Add automation type selection
            }
        },
        confirmButton = {
            TextButton(onClick = onCreate) {
                Text("Create", color = SubtleCyan)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = GreyishTone)
            }
        },
        containerColor = DeepGrey,
        titleContentColor = White,
        textContentColor = GreyishTone
    )
}