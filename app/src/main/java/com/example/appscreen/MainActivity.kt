package com.example.appscreen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appscreen.ui.theme.AppScreenTheme
import com.example.appscreen.ui.theme.DarkBlue
import com.example.appscreen.ui.theme.DeepGrey
import com.example.appscreen.ui.theme.GreyishTone
import com.example.appscreen.ui.theme.LightCyan
import com.example.appscreen.ui.theme.SubtleCyan
import com.example.appscreen.ui.theme.SubtleTeal
import com.example.appscreen.ui.theme.White
import com.example.appscreen.ui.theme.Black
import com.example.appscreen.ui.theme.GradientDarkStart
import com.example.appscreen.ui.theme.GradientDarkEnd
import com.example.appscreen.ui.theme.HeaderSubTextColor
import com.example.appscreen.ui.theme.TabUnselectedTextColor
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.delay

// Remove StatusBarGradientStart as it's no longer used for the notch-only gradient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppScreenTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(onMenuClick: () -> Unit) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = "Hey, User!",
                    style = TextStyle(
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                )
                Text(
                    text = "Saturday, Dec 09, 2023",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        color = HeaderSubTextColor
                    )
                )
            }
        },
        actions = {
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    var selectedItem by remember { mutableStateOf(0) }

    // Root Box to hold the gradient and the Scaffold
    Box(
        modifier = Modifier
            .fillMaxSize()
            // Apply the new full-screen vertical gradient
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        GradientDarkStart, // Lighter dark shade at the top
                        GradientDarkEnd    // Deep black at the bottom
                    )
                )
            )
    ) {
        Scaffold(
            topBar = {
                AppTopAppBar(onMenuClick = { /* TODO: Implement menu action */ })
            },
            bottomBar = {
                BottomNavigationBar(
                    selectedItem = selectedItem,
                    onItemSelected = { index -> selectedItem = index }
                )
            },
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize(),
            content = { scaffoldPaddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(scaffoldPaddingValues)
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    when (selectedItem) {
                        0 -> {
                            WeatherCard()
                            Spacer(modifier = Modifier.height(16.dp))
                            DeviceCategoryTabs()
                            Spacer(modifier = Modifier.height(16.dp))
                            DeviceControlGrid()
                        }
                        1 -> {
                            StatsScreen()
                        }
                        2 -> {
                            SettingsScreen()
                        }
                        3 -> {
                            ProfileScreen()
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun WeatherCard() {
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(Color(0xFF96F2EC), Color(0xFF60DCDC))
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            // Apply shadow with clip = true
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(25.dp), clip = true)
            // Apply background gradient directly to the Card, clipped by its shape
            .background(brush = gradientBrush, shape = RoundedCornerShape(25.dp))
        ,
        colors = CardDefaults.cardColors(
            // Card's own containerColor should be transparent as Modifier.background handles the visual fill
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(25.dp) // Define shape for clipping and layout
    ) {
        // This Box is now for padding and layout of content, not for background
        Box(modifier = Modifier
            .padding(16.dp) /* Padding from Step 3 */
            .fillMaxSize() // Ensure content column takes up available space within padding
        ) {
            Column(modifier = Modifier.fillMaxSize()) { // Column to structure content inside the card
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.WbSunny,
                        contentDescription = "Weather Icon",
                        tint = White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Sunny",
                            style = TextStyle(
                                fontSize = 20.sp, 
                                fontWeight = FontWeight.Medium, 
                                color = White
                            )
                        )
                        Text(
                            text = "Mathura, Uttar Pradesh",
                            style = TextStyle(
                                fontSize = 14.sp, 
                                color = White
                            )
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f)) 
                    Text(
                        text = "40°",
                        style = TextStyle(
                            fontSize = 32.sp, 
                            fontWeight = FontWeight.Bold,
                            color = White
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp)) 
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    WeatherInfoItem(title = "Humidity", value = "35%")
                    WeatherInfoItem(title = "Pressure", value = "1009hpa")
                }
            }
        }
    }
}

@Composable
fun WeatherInfoItem(title: String, value: String) { 
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, color = White)
        )
        Text(
            text = title,
            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal, color = White)
        )
    }
}

@Composable
fun DeviceCategoryTabs() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("All Devices", "Living Room", "Bedroom", "Kitchen")

    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp,
        indicator = {},
        divider = {},
        containerColor = Color.Transparent,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        tabs.forEachIndexed { index, title ->
            val isSelected = selectedTabIndex == index
            val backgroundColor = if (isSelected) Color.White.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.15f)
            // Use named color constant for unselected text
            val textColor = if (isSelected) White else TabUnselectedTextColor
            val fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal

            Tab(
                selected = isSelected,
                onClick = { selectedTabIndex = index },
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .height(38.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = backgroundColor),
                text = {
                    Text(
                        text = title,
                        color = textColor,
                        fontWeight = fontWeight
                    )
                }
            )
        }
    }
}

@Composable
fun DeviceControlGrid() {
    val deviceData = remember {
        mutableStateMapOf(
            "Air Condition" to DeviceInfo(Icons.Filled.AcUnit, "4 Devices", true),
            "Smart TV" to DeviceInfo(Icons.Filled.Tv, "2 Devices", false),
            "Smart Lighting" to DeviceInfo(Icons.Filled.Lightbulb, "8 Devices", false),
            "Speaker" to DeviceInfo(Icons.Filled.Speaker, "6 Devices", false)
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        val deviceNames = deviceData.keys.toList()
        items(
            items = deviceNames,
            key = { deviceName -> deviceName }
        ) { deviceName ->
            val info = deviceData[deviceName]!!
            DeviceCard(
                deviceName = deviceName,
                icon = info.icon,
                deviceCountText = info.countText,
                isDeviceOn = info.isOn,
                onToggle = { newState ->
                    deviceData[deviceName] = info.copy(isOn = newState)
                }
            )
        }
    }
}

data class DeviceInfo(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val countText: String,
    val isOn: Boolean
)

@Composable
fun DeviceCard(
    deviceName: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    deviceCountText: String,
    isDeviceOn: Boolean,
    onToggle: (Boolean) -> Unit
) {
    // Step 3: Use slightly translucent white backgrounds for DeviceCard
    val cardBackgroundColor = if (isDeviceOn) White.copy(alpha = 0.25f) else White.copy(alpha = 0.10f)
    // val cardElevation = if (isDeviceOn) 0.dp else 4.dp // Shadow will be handled by Modifier.shadow

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            // Step 3: Add soft shadow
            // .shadow(elevation = 6.dp, shape = RoundedCornerShape(16.dp), clip = false) // Removed this line
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                onClick = { /* TODO: Handle card click, e.g., navigate to detail screen */ },
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ),
        colors = CardDefaults.cardColors(
            containerColor = cardBackgroundColor // Apply translucent background
        ),
        // elevation = CardDefaults.cardElevation(defaultElevation = cardElevation) // Remove as shadow modifier is used
        shape = RoundedCornerShape(16.dp) // Ensure shape is applied for shadow and clipping
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "$deviceName Icon",
                tint = White,
                modifier = Modifier.size(24.dp)
            )
            Column {
                Text(
                    text = deviceName,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                )
                Text(
                    text = deviceCountText,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = GreyishTone
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isDeviceOn) "ON" else "OFF",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = White
                    )
                )
                Switch(
                    checked = isDeviceOn,
                    onCheckedChange = onToggle,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = DarkBlue,
                        checkedTrackColor = SubtleCyan,
                        uncheckedThumbColor = GreyishTone,
                        uncheckedTrackColor = DeepGrey.copy(alpha = 0.7f)
                    )
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedItem: Int, onItemSelected: (Int) -> Unit) {
    BottomAppBar(
        containerColor = Color.Transparent,
        contentColor = GreyishTone
    ) {
        listOf("Home", "Stats", "Settings", "Profile").forEachIndexed { index, item ->
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
                    imageVector = when (index) {
                        0 -> Icons.Filled.Home
                        1 -> Icons.Filled.Refresh
                        2 -> Icons.Filled.Settings
                        3 -> Icons.Filled.Person
                        else -> Icons.Filled.Home
                    },
                    contentDescription = item,
                    tint = if (isSelected) SubtleCyan else GreyishTone
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

@Composable
fun ShimmerText(
    text: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge.copy(color = White),
    shimmerColors: List<Color> = listOf(
        GreyishTone.copy(alpha = 0.9f),
        GreyishTone.copy(alpha = 0.4f),
        GreyishTone.copy(alpha = 0.9f)
    )
) {
    if (isLoading) {
        // Approximate the text's height for the placeholder
        val textHeight = style.fontSize.value * LocalDensity.current.fontScale * 1.5f // Approximation
        Box(
            modifier = modifier
                .height(textHeight.dp) // Use approximated height
                .fillMaxWidth(0.7f) // Default placeholder width, adjust as needed
                .shimmerEffect(shimmerColors = shimmerColors)
                .background(Color.Transparent) // Ensure background is transparent for shimmer to show
        )
    } else {
        Text(text = text, style = style, modifier = modifier)
    }
}

fun Modifier.shimmerEffect(
    shimmerColors: List<Color> = listOf(
        White.copy(alpha = 0.1f),
        White.copy(alpha = 0.2f),
        White.copy(alpha = 0.1f),
    ),
    durationMillis: Int = 1000
): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "shimmerTransition")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis)
        ), label = "shimmerStartOffsetX"
    )

    background(
        brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
    .onGloballyPositioned {
        size = it.size
    }
}

@Composable
fun StatsScreen() {
    // Initialize isLoading to false if data is considered immediately available for this screen
    var isLoading by remember { mutableStateOf(false) }
    // LaunchedEffect is no longer strictly needed here if isLoading starts as false 
    // and there's no asynchronous data loading simulated or implemented yet for this screen.
    // If you later add actual data loading, you would re-introduce LaunchedEffect.

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShimmerText(
            text = "Device Usage Stats",
            isLoading = isLoading,
            style = MaterialTheme.typography.headlineSmall.copy(color = White, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 24.dp)
        )
        // Placeholder Bar Chart
        if (isLoading) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                (1..4).forEach { _ ->
                    Box(
                        modifier = Modifier
                            .height( ( (0.5f + Math.random() * 0.4f) * 180).dp) // Random height for shimmer
                            .width(40.dp)
                            .shimmerEffect()
                    )
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                val barValues = listOf(0.6f, 0.9f, 0.4f, 0.7f)
                val barColors = listOf(SubtleCyan, LightCyan, SubtleTeal, GreyishTone)
                barValues.forEachIndexed { index, value ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .height((value * 180).dp)
                                .width(40.dp)
                                .background(barColors[index % barColors.size], shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                        )
                        Text(text = "Dev ${index + 1}", color = White, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                    }
                }
            }
        }
        ShimmerText(
            text = "More detailed stats coming soon!",
            isLoading = isLoading,
            style = MaterialTheme.typography.bodyMedium.copy(color = GreyishTone),
            modifier = Modifier.padding(top = 24.dp)
        )
    }
}

@Composable
fun SettingsScreen() {
    // Initialize isLoading to false if data is considered immediately available for this screen
    var isLoading by remember { mutableStateOf(false) }
    // LaunchedEffect removed for now, assuming content is static or loads instantly

    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(true) } // Assuming app is already in dark mode by default

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ShimmerText(
            text = "App Settings",
            isLoading = isLoading,
            style = MaterialTheme.typography.headlineSmall.copy(color = White, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        if (isLoading) {
            Column {
                (1..3).forEach { _ ->
                    Box(modifier = Modifier.fillMaxWidth().height(50.dp).padding(vertical = 8.dp).shimmerEffect())
                }
            }
        } else {
            SettingItem(title = "Enable Notifications", icon = Icons.Filled.Notifications, checked = notificationsEnabled) {
                notificationsEnabled = it
            }
            SettingItem(title = "Dark Mode", icon = Icons.Filled.Brightness4, checked = darkModeEnabled) {
                darkModeEnabled = it
                // TODO: Implement actual theme switching logic if needed
            }
            SettingItem(title = "Account Settings", icon = Icons.Filled.AccountCircle, checked = false, isSwitchVisible = false) {
                // TODO: Navigate to account settings page or show dialog
            }
        }
         ShimmerText(
            text = "More settings available in future updates.",
            isLoading = isLoading,
            style = MaterialTheme.typography.bodyMedium.copy(color = GreyishTone),
            modifier = Modifier.padding(top = 24.dp).align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun SettingItem(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, checked: Boolean, isSwitchVisible: Boolean = true, onCheckedChange: ((Boolean) -> Unit)?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable(enabled = onCheckedChange == null || !isSwitchVisible) { if(!isSwitchVisible && onCheckedChange == null) { /* Handle click for non-switch items */ } },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = title, tint = White, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, style = MaterialTheme.typography.bodyLarge.copy(color = White))
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
            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "Navigate", tint = GreyishTone)
        }
    }
    Divider(color = DeepGrey.copy(alpha = 0.5f), thickness = 1.dp)
}

@Composable
fun ProfileScreen() {
    // Initialize isLoading to false if data is considered immediately available for this screen
    var isLoading by remember { mutableStateOf(false) }
    // LaunchedEffect removed for now, assuming content is static or loads instantly

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Box(modifier = Modifier.fillMaxWidth(0.6f).height(30.dp).shimmerEffect())
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth(0.8f).height(20.dp).shimmerEffect())
        } else {
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
            Text(
                text = "User Name",
                style = MaterialTheme.typography.headlineMedium.copy(color = White, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "user.email@example.com",
                style = MaterialTheme.typography.bodyLarge.copy(color = GreyishTone)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        if (isLoading) {
            Box(modifier = Modifier.fillMaxWidth(0.4f).height(48.dp).clip(RoundedCornerShape(50.dp)).shimmerEffect())
        } else {
            Button(
                onClick = { /* TODO: Handle Log Out */ },
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SubtleCyan)
            ) {
                Text(text = "Log Out", color = DarkBlue, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppScreenTheme {
        MainScreen()
    }
}
