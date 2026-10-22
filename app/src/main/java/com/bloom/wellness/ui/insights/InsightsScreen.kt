package com.bloom.wellness.ui.insights

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bloom.wellness.ui.theme.BloomBadgePink
import com.bloom.wellness.ui.theme.BloomBarInactive
import com.bloom.wellness.ui.theme.BloomBorder
import com.bloom.wellness.ui.theme.BloomCreamBg
import com.bloom.wellness.ui.theme.BloomTerracotta
import com.bloom.wellness.ui.theme.BloomTerracottaLight
import com.bloom.wellness.ui.theme.BloomTextPrimary
import com.bloom.wellness.ui.theme.BloomTextSecondary

/**
 * Exact Figma Insights Screen.
 */
@Composable
fun InsightsScreen() {
    var selectedTimeframe by remember { mutableStateOf("Week") }

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
                text = "Insights",
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = BloomTextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Your week at a glance, and what to try next",
                fontSize = 14.sp,
                color = BloomTextSecondary
            )
        }

        // Scrollable Insights Content
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // 1. Your score card with Bar Chart
            item {
                Column {
                    Text(
                        text = "Your score",
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
                        Column(modifier = Modifier.padding(18.dp)) {
                            // Top Row: Percentage + Week/Month Segmented Switch
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "60%",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 28.sp,
                                        color = BloomTextPrimary
                                    )
                                    Text(
                                        text = "week average",
                                        fontSize = 12.sp,
                                        color = BloomTextSecondary
                                    )
                                }

                                // Segmented Pill: Week | Month
                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(BloomTerracottaLight.copy(alpha = 0.5f))
                                        .padding(3.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(14.dp))
                                            .background(if (selectedTimeframe == "Week") BloomTerracotta else Color.Transparent)
                                            .clickable { selectedTimeframe = "Week" }
                                            .padding(horizontal = 14.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = "Week",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = if (selectedTimeframe == "Week") Color.White else BloomTextSecondary
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(14.dp))
                                            .background(if (selectedTimeframe == "Month") BloomTerracotta else Color.Transparent)
                                            .clickable { selectedTimeframe = "Month" }
                                            .padding(horizontal = 14.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = "Month",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = if (selectedTimeframe == "Month") Color.White else BloomTextSecondary
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // 7 Days Weekly Bar Chart (M, T, W, T, F, S, S)
                            val dayLabels = listOf("M", "T", "W", "T", "F", "S", "S")
                            val barHeights = listOf(48.dp, 72.dp, 56.dp, 66.dp, 68.dp, 75.dp, 10.dp)
                            val isTodayList = listOf(false, false, false, false, false, false, true)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(95.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                dayLabels.forEachIndexed { index, label ->
                                    val height = barHeights[index]
                                    val isToday = isTodayList[index]

                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.width(36.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(height)
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(
                                                    if (isToday) BloomTerracotta else BloomBarInactive
                                                )
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = label,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = if (isToday) BloomTerracotta else BloomTextSecondary
                                        )
                                        if (isToday) {
                                            Box(
                                                modifier = Modifier
                                                    .size(4.dp)
                                                    .clip(CircleShape)
                                                    .background(BloomTerracotta)
                                            )
                                        } else {
                                            Spacer(modifier = Modifier.height(4.dp))
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "Tap a day to see its summary",
                                fontSize = 12.sp,
                                color = BloomTextSecondary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            // 2. Cycle Section
            item {
                Column {
                    Text(
                        text = "Cycle",
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = BloomTextPrimary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BloomBorder)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(BloomBadgePink),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.WaterDrop,
                                    contentDescription = null,
                                    tint = BloomTerracotta,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "No cycle logged yet",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp,
                                    color = BloomTextPrimary
                                )
                                Text(
                                    text = "Log your last period date",
                                    fontSize = 13.sp,
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
                    }
                }
            }

            // 3. Daily Summary Section
            item {
                Column {
                    Text(
                        text = "Daily summary",
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = BloomTextPrimary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BloomBorder)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(BloomTerracotta),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "0%",
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Today",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp,
                                    color = BloomTextPrimary
                                )
                                Text(
                                    text = "Nothing logged yet today — no rush, tap a goal below whenever you're ready.",
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp,
                                    color = BloomTextSecondary
                                )
                            }
                        }
                    }
                }
            }

            // 4. Symptom Patterns Section with Frequency Heat Bars
            item {
                Column {
                    Text(
                        text = "Symptom patterns",
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = BloomTextPrimary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        SymptomPatternCard(
                            name = "Bloating",
                            subtitle = "Moderate or higher on 5 of 7 days",
                            icon = Icons.Outlined.WaterDrop,
                            color = BloomTerracotta,
                            severityPattern = listOf(false, true, true, false, true, true, true)
                        )

                        SymptomPatternCard(
                            name = "Skin & acne",
                            subtitle = "Moderate or higher on 2 of 7 days",
                            icon = Icons.Outlined.Add,
                            color = BloomTerracotta,
                            severityPattern = listOf(false, false, true, false, false, true, false)
                        )

                        SymptomPatternCard(
                            name = "Mood",
                            subtitle = "Moderate or higher on 2 of 7 days",
                            icon = Icons.Outlined.SentimentSatisfied,
                            color = BloomTerracotta,
                            severityPattern = listOf(false, false, true, false, false, false, true)
                        )

                        SymptomPatternCard(
                            name = "Sleep quality",
                            subtitle = "Moderate or higher on 2 of 7 days",
                            icon = Icons.Outlined.Bedtime,
                            color = BloomTerracotta,
                            severityPattern = listOf(false, false, false, true, false, false, true)
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun SymptomPatternCard(
    name: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    severityPattern: List<Boolean>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BloomBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(BloomBadgePink),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = BloomTextPrimary
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = BloomTextSecondary
                )
            }

            // 7 Mini Frequency Vertical Bars
            Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                severityPattern.forEach { isHigh ->
                    Box(
                        modifier = Modifier
                            .width(5.dp)
                            .height(20.dp)
                            .clip(RoundedCornerShape(2.5.dp))
                            .background(
                                if (isHigh) BloomTerracotta else BloomBarInactive
                            )
                    )
                }
            }
        }
    }
}
