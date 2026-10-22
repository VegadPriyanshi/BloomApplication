package com.bloom.wellness.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.DirectionsWalk
import androidx.compose.material.icons.outlined.DinnerDining
import androidx.compose.material.icons.outlined.LunchDining
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bloom.wellness.ui.theme.BloomBadgePink
import com.bloom.wellness.ui.theme.BloomBorder
import com.bloom.wellness.ui.theme.BloomProgressInactive
import com.bloom.wellness.ui.theme.BloomTerracotta
import com.bloom.wellness.ui.theme.BloomTerracottaLight
import com.bloom.wellness.ui.theme.BloomTextPrimary
import com.bloom.wellness.ui.theme.BloomTextSecondary
import com.bloom.wellness.ui.theme.BloomTipBg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloomBottomSheetContainer(
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    content: @Composable () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        containerColor = Color.White,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 8.dp)
                    .size(40.dp, 4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(BloomTerracottaLight)
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 8.dp)
        ) {
            content()
        }
    }
}

/**
 * Common Sheet Header: Serif Italic Title + Close Button
 */
@Composable
fun SheetHeader(
    title: String,
    onClose: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = BloomTextPrimary
        )

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(BloomBadgePink)
                .clickable { onClose() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = BloomTerracotta,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

// ==========================================
// 1. Log Meals Sheet
// ==========================================
@Composable
fun LogMealsSheet(
    onClose: () -> Unit,
    onSave: (Int) -> Unit
) {
    val mealOptions = listOf("Light", "Balanced", "Indulgent", "Skipped")
    var breakfastChoice by remember { mutableStateOf<String?>("Balanced") }
    var lunchChoice by remember { mutableStateOf<String?>("Balanced") }
    var dinnerChoice by remember { mutableStateOf<String?>(null) }

    val loggedCount = listOfNotNull(breakfastChoice, lunchChoice, dinnerChoice).size

    Column(modifier = Modifier.fillMaxWidth()) {
        SheetHeader(title = "Log Meals", onClose = onClose)

        Text(
            text = "How would you describe each meal today?",
            fontSize = 13.sp,
            color = BloomTextSecondary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Breakfast
        MealSection(
            label = "Breakfast",
            icon = Icons.Outlined.WbSunny,
            options = mealOptions,
            selected = breakfastChoice,
            onSelect = { breakfastChoice = it }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Lunch
        MealSection(
            label = "Lunch",
            icon = Icons.Outlined.LunchDining,
            options = mealOptions,
            selected = lunchChoice,
            onSelect = { lunchChoice = it }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Dinner
        MealSection(
            label = "Dinner",
            icon = Icons.Outlined.DinnerDining,
            options = mealOptions,
            selected = dinnerChoice,
            onSelect = { dinnerChoice = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "$loggedCount of 3 meals logged today",
            fontSize = 13.sp,
            color = BloomTextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        Button(
            onClick = {
                onSave(loggedCount)
                onClose()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BloomTerracotta)
        ) {
            Text(text = "Save", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
    }
}

@Composable
private fun MealSection(
    label: String,
    icon: ImageVector,
    options: List<String>,
    selected: String?,
    onSelect: (String) -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = BloomTerracotta,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = BloomTextPrimary
            )
        }

        // 2x2 grid of pill options
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.take(2).forEach { option ->
                MealPill(
                    text = option,
                    isSelected = selected == option,
                    onClick = { onSelect(option) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.drop(2).forEach { option ->
                MealPill(
                    text = option,
                    isSelected = selected == option,
                    onClick = { onSelect(option) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun MealPill(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(42.dp)
            .clip(RoundedCornerShape(21.dp))
            .border(
                width = if (isSelected) 1.5.dp else 1.dp,
                color = if (isSelected) BloomTerracotta else BloomBorder,
                shape = RoundedCornerShape(21.dp)
            )
            .background(if (isSelected) BloomBadgePink else Color.White)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isSelected) BloomTerracotta else BloomTextPrimary
        )
    }
}

// ==========================================
// 2. Log Movement Sheet
// ==========================================
@Composable
fun LogMovementSheet(
    currentMinutes: Int,
    targetMinutes: Int,
    onClose: () -> Unit,
    onSave: (Int) -> Unit
) {
    var minutes by remember { mutableIntStateOf(currentMinutes) }

    Column(modifier = Modifier.fillMaxWidth()) {
        SheetHeader(title = "Log Movement", onClose = onClose)

        Text(
            text = "Today's movement",
            fontSize = 13.sp,
            color = BloomTextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Center Stepper
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundStepperButton(
                icon = Icons.Outlined.Remove,
                onClick = { minutes = (minutes - 5).coerceAtLeast(0) }
            )
            Spacer(modifier = Modifier.width(20.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "$minutes",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    color = BloomTextPrimary
                )
                Text(
                    text = " min",
                    fontSize = 16.sp,
                    color = BloomTextSecondary,
                    modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            RoundStepperButton(
                icon = Icons.Outlined.Add,
                onClick = { minutes += 5 }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Goal: $targetMinutes min · adjust in Settings",
            fontSize = 12.sp,
            color = BloomTextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Tip Card with +10 min quick action
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = BloomTipBg),
            border = BorderStroke(1.dp, Color(0xFFEBDDCF))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.DirectionsWalk,
                        contentDescription = null,
                        tint = BloomTerracotta,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Get moving today",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = BloomTextPrimary
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "No movement logged yet today. Try a brisk 30-minute walk, a swim, or some light strength training — these are especially good for insulin sensitivity with PCOS.",
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = BloomTextSecondary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, BloomTerracotta, RoundedCornerShape(14.dp))
                        .clickable { minutes += 10 }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "+10 min",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BloomTerracotta
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                onSave(minutes)
                onClose()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BloomTerracotta)
        ) {
            Text(text = "Save", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
    }
}

// ==========================================
// 3. Log Water Sheet
// ==========================================
@Composable
fun LogWaterSheet(
    currentGlasses: Int,
    targetGlasses: Int,
    onClose: () -> Unit,
    onSave: (Int) -> Unit
) {
    var glasses by remember { mutableIntStateOf(currentGlasses) }

    Column(modifier = Modifier.fillMaxWidth()) {
        SheetHeader(title = "Log Water", onClose = onClose)

        Text(
            text = "Today's water",
            fontSize = 13.sp,
            color = BloomTextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundStepperButton(
                icon = Icons.Outlined.Remove,
                onClick = { glasses = (glasses - 1).coerceAtLeast(0) }
            )
            Spacer(modifier = Modifier.width(20.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "$glasses",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    color = BloomTextPrimary
                )
                Text(
                    text = " glasses",
                    fontSize = 16.sp,
                    color = BloomTextSecondary,
                    modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            RoundStepperButton(
                icon = Icons.Outlined.Add,
                onClick = { glasses += 1 }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Goal: $targetGlasses glasses · adjust in Settings",
            fontSize = 12.sp,
            color = BloomTextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(18.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = BloomTipBg),
            border = BorderStroke(1.dp, Color(0xFFEBDDCF))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.WaterDrop,
                        contentDescription = null,
                        tint = BloomTerracotta,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Start hydrating",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = BloomTextPrimary
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "No water logged yet today. Try starting with a glass first thing in the morning.",
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = BloomTextSecondary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, BloomTerracotta, RoundedCornerShape(14.dp))
                        .clickable { glasses += 1 }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "+1 glasses",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BloomTerracotta
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                onSave(glasses)
                onClose()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BloomTerracotta)
        ) {
            Text(text = "Save", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
    }
}

// ==========================================
// 4. Log Relaxation Sheet
// ==========================================
@Composable
fun LogRelaxationSheet(
    currentMinutes: Int,
    targetMinutes: Int,
    onClose: () -> Unit,
    onSave: (Int) -> Unit
) {
    var minutes by remember { mutableIntStateOf(currentMinutes) }

    Column(modifier = Modifier.fillMaxWidth()) {
        SheetHeader(title = "Log Relaxation", onClose = onClose)

        Text(
            text = "Today's relaxation",
            fontSize = 13.sp,
            color = BloomTextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundStepperButton(
                icon = Icons.Outlined.Remove,
                onClick = { minutes = (minutes - 5).coerceAtLeast(0) }
            )
            Spacer(modifier = Modifier.width(20.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "$minutes",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    color = BloomTextPrimary
                )
                Text(
                    text = " min",
                    fontSize = 16.sp,
                    color = BloomTextSecondary,
                    modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            RoundStepperButton(
                icon = Icons.Outlined.Add,
                onClick = { minutes += 5 }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Goal: $targetMinutes min · adjust in Settings",
            fontSize = 12.sp,
            color = BloomTextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(18.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = BloomTipBg),
            border = BorderStroke(1.dp, Color(0xFFEBDDCF))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Spa,
                        contentDescription = null,
                        tint = BloomTerracotta,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Make time to unwind",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = BloomTextPrimary
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "No relaxation time logged yet. Even 5-10 minutes of deep breathing, meditation, or gentle yoga can help lower stress hormones.",
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = BloomTextSecondary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, BloomTerracotta, RoundedCornerShape(14.dp))
                        .clickable { minutes += 5 }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "+5 min",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BloomTerracotta
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                onSave(minutes)
                onClose()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BloomTerracotta)
        ) {
            Text(text = "Save", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
    }
}

// ==========================================
// 5. Log Sleep Sheet
// ==========================================
@Composable
fun LogSleepSheet(
    currentHours: Int,
    targetHours: Int,
    onClose: () -> Unit,
    onSave: (Int) -> Unit
) {
    var hours by remember { mutableIntStateOf(currentHours) }

    Column(modifier = Modifier.fillMaxWidth()) {
        SheetHeader(title = "Log Sleep", onClose = onClose)

        Text(
            text = "Today's sleep",
            fontSize = 13.sp,
            color = BloomTextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundStepperButton(
                icon = Icons.Outlined.Remove,
                onClick = { hours = (hours - 1).coerceAtLeast(0) }
            )
            Spacer(modifier = Modifier.width(20.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "$hours",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    color = BloomTextPrimary
                )
                Text(
                    text = " hrs",
                    fontSize = 16.sp,
                    color = BloomTextSecondary,
                    modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            RoundStepperButton(
                icon = Icons.Outlined.Add,
                onClick = { hours += 1 }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Goal: $targetHours hrs · adjust in Settings",
            fontSize = 12.sp,
            color = BloomTextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(18.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = BloomTipBg),
            border = BorderStroke(1.dp, Color(0xFFEBDDCF))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Bedtime,
                        contentDescription = null,
                        tint = BloomTerracotta,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Log last night's sleep",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = BloomTextPrimary
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "You haven't logged sleep yet. Consistent, close-to-8-hr nights can help keep your hormones more balanced — avoiding phones and screens before bed makes it easier to fall asleep.",
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = BloomTextSecondary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, BloomTerracotta, RoundedCornerShape(14.dp))
                        .clickable { hours += 1 }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "+1 hrs",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BloomTerracotta
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                onSave(hours)
                onClose()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BloomTerracotta)
        ) {
            Text(text = "Save", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
    }
}

// ==========================================
// 6. Log Cycle Sheet (Interactive Calendar)
// ==========================================
@Composable
fun LogCycleSheet(
    onClose: () -> Unit,
    onSave: (Int) -> Unit
) {
    var selectedDay by remember { mutableIntStateOf(30) }

    Column(modifier = Modifier.fillMaxWidth()) {
        SheetHeader(title = "Log Cycle", onClose = onClose)

        Text(
            text = "When did your last period start?",
            fontSize = 13.sp,
            color = BloomTextSecondary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Calendar Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, BloomBorder)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                // Month Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(BloomBadgePink),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = null,
                            tint = BloomTerracotta,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Text(
                        text = "July 2026",
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = BloomTerracotta
                    )

                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(BloomBadgePink),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = BloomTerracotta,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Day Labels
                val days = listOf("M", "T", "W", "T", "F", "S", "S")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    days.forEach { d ->
                        Text(
                            text = d,
                            fontSize = 11.sp,
                            color = BloomTextSecondary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 31 Days Grid
                val gridRows = listOf(
                    listOf(null, null, 1, 2, 3, 4, 5),
                    listOf(6, 7, 8, 9, 10, 11, 12),
                    listOf(13, 14, 15, 16, 17, 18, 19),
                    listOf(20, 21, 22, 23, 24, 25, 26),
                    listOf(27, 28, 29, 30, 31, null, null)
                )

                gridRows.forEach { week ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        week.forEach { dayNum ->
                            if (dayNum != null) {
                                val isSelected = dayNum == selectedDay
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isSelected) BloomTerracotta else Color.Transparent
                                        )
                                        .clickable { selectedDay = dayNum },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "$dayNum",
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) Color.White else BloomTextPrimary
                                    )
                                }
                            } else {
                                Spacer(modifier = Modifier.size(32.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Legend
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(BloomTerracotta)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Last period start", fontSize = 11.sp, color = BloomTextSecondary)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .border(1.dp, BloomTerracotta, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Next expected", fontSize = 11.sp, color = BloomTextSecondary)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Info Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BloomTipBg),
            border = BorderStroke(1.dp, Color(0xFFEBDDCF))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.WaterDrop,
                        contentDescription = null,
                        tint = BloomTerracotta,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Log your last period",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = BloomTextPrimary
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tap a date on the calendar above and Bloom will estimate your next period and let you know if it looks delayed.",
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = BloomTextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onSave(selectedDay)
                onClose()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BloomTerracotta)
        ) {
            Text(text = "Done", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
    }
}

// ==========================================
// 7. Today's Symptoms Sheet
// ==========================================
@Composable
fun TodaysSymptomsSheet(
    onClose: () -> Unit,
    onSave: (Map<String, String>) -> Unit
) {
    val symptomState = remember {
        mutableStateMapOf(
            "Bloating" to "Mild",
            "Skin & acne" to "Clear",
            "Mood" to "Great",
            "Sleep quality" to "Okay"
        )
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        SheetHeader(title = "Today's symptoms", onClose = onClose)

        Text(
            text = "These are separate from your daily goals — logging them just helps you (and eventually your doctor) spot patterns over time.",
            fontSize = 12.sp,
            lineHeight = 17.sp,
            color = BloomTextSecondary,
            modifier = Modifier.padding(bottom = 14.dp)
        )

        // Bloating
        SymptomChoiceRow(
            label = "Bloating",
            icon = Icons.Outlined.WaterDrop,
            options = listOf("None", "Mild", "Moderate", "Severe"),
            selected = symptomState["Bloating"] ?: "None",
            onSelect = { symptomState["Bloating"] = it }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Skin & acne
        SymptomChoiceRow(
            label = "Skin & acne",
            icon = Icons.Outlined.Add,
            options = listOf("Clear", "Mild", "Moderate", "Flare-up"),
            selected = symptomState["Skin & acne"] ?: "Clear",
            onSelect = { symptomState["Skin & acne"] = it }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Mood
        SymptomChoiceRow(
            label = "Mood",
            icon = Icons.Outlined.SentimentSatisfied,
            options = listOf("Great", "Okay", "Low", "Very low"),
            selected = symptomState["Mood"] ?: "Great",
            onSelect = { symptomState["Mood"] = it }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Sleep quality
        SymptomChoiceRow(
            label = "Sleep quality",
            icon = Icons.Outlined.Bedtime,
            options = listOf("Great", "Okay", "Poor", "Very poor"),
            selected = symptomState["Sleep quality"] ?: "Okay",
            onSelect = { symptomState["Sleep quality"] = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                onSave(symptomState.toMap())
                onClose()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BloomTerracotta)
        ) {
            Text(text = "Done", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
    }
}

@Composable
private fun SymptomChoiceRow(
    label: String,
    icon: ImageVector,
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = BloomTerracotta,
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = BloomTextPrimary
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            options.forEach { opt ->
                val isSelected = selected == opt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp)
                        .clip(RoundedCornerShape(19.dp))
                        .border(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            color = if (isSelected) BloomTerracotta else BloomBorder,
                            shape = RoundedCornerShape(19.dp)
                        )
                        .background(if (isSelected) BloomBadgePink else Color.White)
                        .clickable { onSelect(opt) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = opt,
                        fontSize = 11.5.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (isSelected) BloomTerracotta else BloomTextPrimary
                    )
                }
            }
        }
    }
}

// ==========================================
// 8. Edit Profile Sheet
// ==========================================
@Composable
fun EditProfileSheet(
    currentName: String,
    onClose: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var name by remember { mutableStateOf(currentName) }
    var selectedStage by remember { mutableStateOf("Diagnosed") }

    val stages = listOf(
        Pair("Diagnosed", "I have a confirmed PCOS diagnosis"),
        Pair("Suspected", "A doctor mentioned it, not confirmed"),
        Pair("Exploring", "I think I might have symptoms"),
        Pair("Not sure yet", "I just want to learn more")
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        SheetHeader(title = "Edit Profile", onClose = onClose)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BloomTerracotta,
                unfocusedBorderColor = BloomBorder
            )
        )

        Text(
            text = "Journey stage",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = BloomTextPrimary,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
        )

        stages.forEach { (title, subtitle) ->
            val isSelected = selectedStage == title
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { selectedStage = title },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(
                    width = if (isSelected) 1.5.dp else 1.dp,
                    color = if (isSelected) BloomTerracotta else BloomBorder
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .border(
                                width = if (isSelected) 4.5.dp else 1.5.dp,
                                color = if (isSelected) BloomTerracotta else BloomTextSecondary.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = title,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = BloomTextPrimary
                        )
                        Text(
                            text = subtitle,
                            fontSize = 12.sp,
                            color = BloomTextSecondary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {
                onSave(name.ifBlank { "Sofia" }, selectedStage)
                onClose()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BloomTerracotta)
        ) {
            Text(text = "Save", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
    }
}

// ==========================================
// 9. Reminder Time Sheet
// ==========================================
@Composable
fun ReminderTimeSheet(
    currentTime: String,
    onClose: () -> Unit,
    onSave: (String) -> Unit
) {
    var selectedId by remember { mutableStateOf("morning") }
    var customTime by remember { mutableStateOf(currentTime) }

    val options = listOf(
        Triple("morning", "Morning", "7:30 AM — start the day on track"),
        Triple("afternoon", "Afternoon", "1:00 PM — a midday check-in"),
        Triple("evening", "Evening", "8:00 PM — wind down and reflect")
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        SheetHeader(title = "Reminder Time", onClose = onClose)

        Text(
            text = "When should Bloom nudge you to log your day?",
            fontSize = 13.sp,
            color = BloomTextSecondary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        options.forEach { (id, title, subtitle) ->
            val isSelected = selectedId == id
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { selectedId = id },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(
                    width = if (isSelected) 1.5.dp else 1.dp,
                    color = if (isSelected) BloomTerracotta else BloomBorder
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .border(
                                width = if (isSelected) 4.5.dp else 1.5.dp,
                                color = if (isSelected) BloomTerracotta else BloomTextSecondary.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = title,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = BloomTextPrimary
                        )
                        Text(
                            text = subtitle,
                            fontSize = 12.sp,
                            color = BloomTextSecondary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Or set a custom time",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = BloomTextPrimary,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { selectedId = "custom" },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(
                width = if (selectedId == "custom") 1.5.dp else 1.dp,
                color = if (selectedId == "custom") BloomTerracotta else BloomBorder
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = customTime,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = BloomTextPrimary
                )
                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = null,
                    tint = BloomTextSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val saved = when (selectedId) {
                    "morning" -> "07:30 AM"
                    "afternoon" -> "01:00 PM"
                    "evening" -> "08:00 PM"
                    else -> customTime
                }
                onSave(saved)
                onClose()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BloomTerracotta)
        ) {
            Text(text = "Save", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
    }
}

// ==========================================
// 10. Live Notification Preview Sheet
// ==========================================
@Composable
fun LiveNotificationPreviewSheet(
    onClose: () -> Unit
) {
    var activeCategory by remember { mutableStateOf("Meditation") }
    var isRunning by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxWidth()) {
        SheetHeader(title = "Live Notification Preview", onClose = onClose)

        Text(
            text = "A preview of how Bloom could show live, real-time updates on the lock screen.",
            fontSize = 12.sp,
            lineHeight = 17.sp,
            color = BloomTextSecondary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Filter Pills: Meditation | Daily goal | Water
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Meditation", "Daily goal", "Water").forEach { cat ->
                val isSelected = activeCategory == cat
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .border(
                            1.dp,
                            if (isSelected) BloomTerracotta else BloomBorder,
                            RoundedCornerShape(18.dp)
                        )
                        .background(if (isSelected) BloomBadgePink else Color.White)
                        .clickable { activeCategory = cat },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cat,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (isSelected) BloomTerracotta else BloomTextPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Dark Simulated Lockscreen Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF141416))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "5:52 AM",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Thu, Jul 30",
                    fontSize = 12.sp,
                    color = Color(0xFFA0A0A5)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Notification Bubble
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF242428))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .background(BloomTerracotta)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Bloom",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }
                            Text(
                                text = "now",
                                fontSize = 11.sp,
                                color = Color(0xFFA0A0A5)
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = if (activeCategory == "Meditation") "Meditation: 5:00 remaining" else "$activeCategory session active",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress = { if (isRunning) 0.65f else 0.0f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp)),
                            color = BloomTerracotta,
                            trackColor = Color(0xFF38383F)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Action Buttons: Begin | Reset
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { isRunning = true },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BloomTerracotta)
            ) {
                Text(text = "Begin", fontWeight = FontWeight.SemiBold)
            }

            Button(
                onClick = { isRunning = false },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BloomBadgePink,
                    contentColor = BloomTerracotta
                )
            ) {
                Text(text = "Reset", fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Updates live while a session is running — same pattern as an order-tracking or workout notification.",
            fontSize = 11.5.sp,
            lineHeight = 16.sp,
            color = BloomTextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun RoundStepperButton(
    icon: ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .border(1.dp, BloomBorder, CircleShape)
            .background(Color.White)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = BloomTerracotta,
            modifier = Modifier.size(20.dp)
        )
    }
}
