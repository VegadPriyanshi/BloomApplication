package com.bloom.wellness.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.DirectionsWalk
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bloom.wellness.data.preferences.OnboardingPreferences
import com.bloom.wellness.ui.components.BloomBottomSheetContainer
import com.bloom.wellness.ui.components.EditProfileSheet
import com.bloom.wellness.ui.components.LiveNotificationPreviewSheet
import com.bloom.wellness.ui.components.ReminderTimeSheet
import com.bloom.wellness.ui.theme.BloomBorder
import com.bloom.wellness.ui.theme.BloomCreamBg
import com.bloom.wellness.ui.theme.BloomTerracotta
import com.bloom.wellness.ui.theme.BloomTerracottaLight
import com.bloom.wellness.ui.theme.BloomTerracottaPressed
import com.bloom.wellness.ui.theme.BloomTextPrimary
import com.bloom.wellness.ui.theme.BloomTextSecondary
import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.coroutines.launch

enum class SettingsSheetType {
    EDIT_PROFILE,
    REMINDER_TIME,
    LIVE_NOTIFICATION_PREVIEW
}

/**
 * Exact Figma Settings screen with tracking toggles, goal steppers, interactive sheets, and sign-out.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    preferences: OnboardingPreferences,
    onSignOut: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val userName by preferences.userName.collectAsState(initial = "Sofia")
    val reminderTime by preferences.reminderTime.collectAsState(initial = "8:00 AM")
    val notificationsEnabled by preferences.notificationsEnabled.collectAsState(initial = true)

    val mealsTarget by preferences.mealsTarget.collectAsState(initial = 3)
    val movementTarget by preferences.movementTarget.collectAsState(initial = 30)
    val waterTarget by preferences.waterTarget.collectAsState(initial = 8)
    val relaxationTarget by preferences.relaxationTarget.collectAsState(initial = 15)
    val sleepTarget by preferences.sleepTarget.collectAsState(initial = 8)

    var activeSettingsSheet by remember { mutableStateOf<SettingsSheetType?>(null) }

    // Tracking switches state
    val trackingMap = remember {
        mutableStateMapOf(
            "Meals" to true,
            "Movement" to true,
            "Water" to true,
            "Relaxation" to true,
            "Sleep" to true,
            "Cycle" to true,
            "Symptom check-in" to true
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomCreamBg)
            .statusBarsPadding()
    ) {
        // Title Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Settings",
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = BloomTextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Your profile and daily goal targets",
                fontSize = 14.sp,
                color = BloomTextSecondary
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // 1. Profile Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BloomBorder)
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(BloomTerracottaLight.copy(alpha = 0.7f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Person,
                                    contentDescription = "Avatar",
                                    tint = BloomTerracotta,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = userName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = BloomTextPrimary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "PCOS journey · Not set",
                                    fontSize = 12.sp,
                                    color = BloomTextSecondary
                                )
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = BloomBorder.copy(alpha = 0.6f)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { activeSettingsSheet = SettingsSheetType.EDIT_PROFILE }
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Edit profile",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = BloomTextPrimary
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null,
                                tint = BloomTextSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            // 2. What you're tracking Section
            item {
                Column {
                    Text(
                        text = "What you're tracking",
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = BloomTextPrimary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BloomBorder)
                    ) {
                        Column {
                            val items = listOf(
                                Triple("Meals", Icons.Outlined.Restaurant, Color(0xFFF9EAE7)),
                                Triple("Movement", Icons.AutoMirrored.Outlined.DirectionsWalk, Color(0xFFEAF4ED)),
                                Triple("Water", Icons.Outlined.WaterDrop, Color(0xFFEAF1F5)),
                                Triple("Relaxation", Icons.Outlined.Spa, Color(0xFFF0ECF5)),
                                Triple("Sleep", Icons.Outlined.Bedtime, Color(0xFFE8E9F2)),
                                Triple("Cycle", Icons.Outlined.WaterDrop, Color(0xFFF9EAE7)),
                                Triple("Symptom check-in", Icons.Outlined.SentimentSatisfied, Color(0xFFFEF4E8))
                            )

                            items.forEachIndexed { index, (label, icon, badgeColor) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(36.dp)
                                                .clip(RoundedCornerShape(10.dp))
                                                .background(badgeColor),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = icon,
                                                contentDescription = label,
                                                tint = BloomTextPrimary,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(14.dp))
                                        Text(
                                            text = label,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 14.sp,
                                            color = BloomTextPrimary
                                        )
                                    }

                                    val isChecked = trackingMap[label] ?: true
                                    Switch(
                                        checked = isChecked,
                                        onCheckedChange = { trackingMap[label] = it },
                                        colors = SwitchDefaults.colors(
                                            checkedThumbColor = Color.White,
                                            checkedTrackColor = BloomTerracotta,
                                            uncheckedThumbColor = Color.White,
                                            uncheckedTrackColor = BloomBorder
                                        )
                                    )
                                }

                                if (index < items.size - 1) {
                                    HorizontalDivider(
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        color = BloomBorder.copy(alpha = 0.5f)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 3. Daily Goal Targets Section with Steppers
            item {
                Column {
                    Text(
                        text = "Daily goal targets",
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = BloomTextPrimary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BloomBorder)
                    ) {
                        Column {
                            // Meals stepper
                            StepperRow(
                                label = "Meals",
                                valueText = "$mealsTarget meals",
                                icon = Icons.Outlined.Restaurant,
                                badgeColor = Color(0xFFF9EAE7),
                                onDecrement = {
                                    coroutineScope.launch {
                                        preferences.setTarget("meals", (mealsTarget - 1).coerceAtLeast(1))
                                    }
                                },
                                onIncrement = {
                                    coroutineScope.launch {
                                        preferences.setTarget("meals", (mealsTarget + 1).coerceAtMost(6))
                                    }
                                }
                            )

                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = BloomBorder.copy(alpha = 0.5f)
                            )

                            // Movement stepper
                            StepperRow(
                                label = "Movement",
                                valueText = "$movementTarget min",
                                icon = Icons.AutoMirrored.Outlined.DirectionsWalk,
                                badgeColor = Color(0xFFEAF4ED),
                                onDecrement = {
                                    coroutineScope.launch {
                                        preferences.setTarget("movement", (movementTarget - 5).coerceAtLeast(5))
                                    }
                                },
                                onIncrement = {
                                    coroutineScope.launch {
                                        preferences.setTarget("movement", (movementTarget + 5).coerceAtMost(120))
                                    }
                                }
                            )

                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = BloomBorder.copy(alpha = 0.5f)
                            )

                            // Water stepper
                            StepperRow(
                                label = "Water",
                                valueText = "$waterTarget glasses",
                                icon = Icons.Outlined.WaterDrop,
                                badgeColor = Color(0xFFEAF1F5),
                                onDecrement = {
                                    coroutineScope.launch {
                                        preferences.setTarget("water", (waterTarget - 1).coerceAtLeast(1))
                                    }
                                },
                                onIncrement = {
                                    coroutineScope.launch {
                                        preferences.setTarget("water", (waterTarget + 1).coerceAtMost(16))
                                    }
                                }
                            )

                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = BloomBorder.copy(alpha = 0.5f)
                            )

                            // Relaxation stepper
                            StepperRow(
                                label = "Relaxation",
                                valueText = "$relaxationTarget min",
                                icon = Icons.Outlined.Spa,
                                badgeColor = Color(0xFFF0ECF5),
                                onDecrement = {
                                    coroutineScope.launch {
                                        preferences.setTarget("relaxation", (relaxationTarget - 5).coerceAtLeast(5))
                                    }
                                },
                                onIncrement = {
                                    coroutineScope.launch {
                                        preferences.setTarget("relaxation", (relaxationTarget + 5).coerceAtMost(60))
                                    }
                                }
                            )

                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = BloomBorder.copy(alpha = 0.5f)
                            )

                            // Sleep stepper
                            StepperRow(
                                label = "Sleep",
                                valueText = "$sleepTarget hrs",
                                icon = Icons.Outlined.Bedtime,
                                badgeColor = Color(0xFFE8E9F2),
                                onDecrement = {
                                    coroutineScope.launch {
                                        preferences.setTarget("sleep", (sleepTarget - 1).coerceAtLeast(4))
                                    }
                                },
                                onIncrement = {
                                    coroutineScope.launch {
                                        preferences.setTarget("sleep", (sleepTarget + 1).coerceAtMost(12))
                                    }
                                }
                            )
                        }
                    }
                }
            }

            // 4. Notifications & Preferences Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BloomBorder)
                ) {
                    Column {
                        // Notifications Switch Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Notifications",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp,
                                    color = BloomTextPrimary
                                )
                                Text(
                                    text = "Daily reminders to log",
                                    fontSize = 12.sp,
                                    color = BloomTextSecondary
                                )
                            }
                            Switch(
                                checked = notificationsEnabled,
                                onCheckedChange = {
                                    coroutineScope.launch {
                                        preferences.setNotificationsEnabled(it)
                                    }
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = BloomTerracotta,
                                    uncheckedThumbColor = Color.White,
                                    uncheckedTrackColor = BloomBorder
                                )
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = BloomBorder.copy(alpha = 0.5f)
                        )

                        // Reminder Time Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { activeSettingsSheet = SettingsSheetType.REMINDER_TIME }
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Reminder time",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp,
                                    color = BloomTextPrimary
                                )
                                Text(
                                    text = reminderTime,
                                    fontSize = 12.sp,
                                    color = BloomTextSecondary
                                )
                            }
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null,
                                tint = BloomTextSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = BloomBorder.copy(alpha = 0.5f)
                        )

                        // Live Notification Preview
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { activeSettingsSheet = SettingsSheetType.LIVE_NOTIFICATION_PREVIEW }
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Live notification preview",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp,
                                    color = BloomTextPrimary
                                )
                                Text(
                                    text = "See lock-screen updates",
                                    fontSize = 12.sp,
                                    color = BloomTextSecondary
                                )
                            }
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null,
                                tint = BloomTextSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = BloomBorder.copy(alpha = 0.5f)
                        )

                        // App Version Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "App version",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = BloomTextPrimary
                            )
                            Text(
                                text = "1.0 · Prototype",
                                fontSize = 13.sp,
                                color = BloomTextSecondary
                            )
                        }
                    }
                }
            }

            // 5. Sign Out Button
            item {
                OutlinedButton(
                    onClick = {
                        coroutineScope.launch {
                            preferences.resetAll()
                            onSignOut()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(18.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BloomTerracottaPressed),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = BloomTerracottaPressed)
                ) {
                    Text(
                        text = "Sign out",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = BloomTerracottaPressed
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }

        // Interactive Bottom Sheets from Figma
        activeSettingsSheet?.let { sheet ->
            BloomBottomSheetContainer(
                onDismissRequest = { activeSettingsSheet = null }
            ) {
                when (sheet) {
                    SettingsSheetType.EDIT_PROFILE -> EditProfileSheet(
                        currentName = userName,
                        onClose = { activeSettingsSheet = null },
                        onSave = { newName, stage ->
                            coroutineScope.launch {
                                preferences.setUserName(newName)
                            }
                        }
                    )
                    SettingsSheetType.REMINDER_TIME -> ReminderTimeSheet(
                        currentTime = reminderTime,
                        onClose = { activeSettingsSheet = null },
                        onSave = { newTime ->
                            coroutineScope.launch {
                                preferences.setReminderTime(newTime)
                            }
                        }
                    )
                    SettingsSheetType.LIVE_NOTIFICATION_PREVIEW -> LiveNotificationPreviewSheet(
                        onClose = { activeSettingsSheet = null }
                    )
                }
            }
        }
    }
}

@Composable
private fun StepperRow(
    label: String,
    valueText: String,
    icon: ImageVector,
    badgeColor: Color,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(badgeColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = BloomTextPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = label,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = BloomTextPrimary
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Minus Button
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .border(1.dp, BloomBorder, CircleShape)
                    .clickable { onDecrement() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Remove,
                    contentDescription = "Decrease",
                    tint = BloomTerracotta,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = valueText,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = BloomTextPrimary,
                modifier = Modifier.width(76.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.width(10.dp))

            // Plus Button
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .border(1.dp, BloomBorder, CircleShape)
                    .clickable { onIncrement() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Increase",
                    tint = BloomTerracotta,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
