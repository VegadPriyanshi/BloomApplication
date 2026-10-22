package com.bloom.wellness.ui.home

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.automirrored.outlined.DirectionsWalk
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateOf
import com.bloom.wellness.ads.AdaptiveBannerAd
import com.bloom.wellness.ui.components.BloomBottomSheetContainer
import com.bloom.wellness.ui.components.BloomFlowerGraphic
import com.bloom.wellness.ui.components.CircularProgressRing
import com.bloom.wellness.ui.components.LogCycleSheet
import com.bloom.wellness.ui.components.LogMealsSheet
import com.bloom.wellness.ui.components.LogMovementSheet
import com.bloom.wellness.ui.components.LogRelaxationSheet
import com.bloom.wellness.ui.components.LogSleepSheet
import com.bloom.wellness.ui.components.LogWaterSheet
import com.bloom.wellness.ui.components.TodaysSymptomsSheet
import com.bloom.wellness.ui.theme.BloomBadgePink
import com.bloom.wellness.ui.theme.BloomBorder
import com.bloom.wellness.ui.theme.BloomCreamBg
import com.bloom.wellness.ui.theme.BloomGoalMeals
import com.bloom.wellness.ui.theme.BloomGoalMovement
import com.bloom.wellness.ui.theme.BloomGoalRelaxation
import com.bloom.wellness.ui.theme.BloomGoalSleep
import com.bloom.wellness.ui.theme.BloomGoalWater
import com.bloom.wellness.ui.theme.BloomProgressInactive
import com.bloom.wellness.ui.theme.BloomTerracotta
import com.bloom.wellness.ui.theme.BloomTerracottaLight
import com.bloom.wellness.ui.theme.BloomTextPrimary
import com.bloom.wellness.ui.theme.BloomTextSecondary
import com.bloom.wellness.ui.theme.BloomTipBg
import com.bloom.wellness.ui.theme.BloomTipBrown
import com.bloom.wellness.ui.theme.BloomTipDoneGreen

import androidx.compose.material3.ExperimentalMaterial3Api

enum class HomeSheetType {
    MEALS,
    MOVEMENT,
    WATER,
    RELAXATION,
    SLEEP,
    CYCLE,
    SYMPTOMS
}

/**
 * Exact Figma Home Dashboard screen with interactive bottom sheet logging.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToSettings: () -> Unit = {},
    onNavigateToLearn: () -> Unit = {}
) {
    val userName by viewModel.userName.collectAsState()
    val completedCount by viewModel.completedGoalsCount.collectAsState()
    val mealsLogged by viewModel.mealsLogged.collectAsState()
    val mealsTarget by viewModel.mealsTarget.collectAsState()
    val movementLogged by viewModel.movementLogged.collectAsState()
    val movementTarget by viewModel.movementTarget.collectAsState()
    val waterLogged by viewModel.waterLogged.collectAsState()
    val waterTarget by viewModel.waterTarget.collectAsState()
    val relaxationLogged by viewModel.relaxationLogged.collectAsState()
    val relaxationTarget by viewModel.relaxationTarget.collectAsState()
    val sleepLogged by viewModel.sleepLogged.collectAsState()
    val sleepTarget by viewModel.sleepTarget.collectAsState()
    val isTipDone by viewModel.isTipDone.collectAsState()

    var activeFaqIndex by remember { mutableIntStateOf(0) }
    var activeSheet by remember { mutableStateOf<HomeSheetType?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomCreamBg)
            .statusBarsPadding()
    ) {
        // Top Greeting Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "GOOD MORNING,",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp,
                    color = BloomTextSecondary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = userName,
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = BloomTextPrimary
                )
            }

            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(BloomTerracottaLight.copy(alpha = 0.7f))
                    .clickable { onNavigateToSettings() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Profile",
                    tint = BloomTerracotta,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Scrollable Feed
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Today's Goals Card with Flower Watermark & "Start here"
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BloomBorder)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        // Background Flower Watermark on Right
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            BloomFlowerGraphic(
                                modifier = Modifier.alpha(0.35f),
                                size = 110.dp,
                                showSparkles = false,
                                showGlow = false
                            )
                            Text(
                                text = "Start here",
                                fontFamily = FontFamily.Serif,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = BloomTerracotta
                            )
                        }

                        // Content on Left
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.68f)
                                .padding(20.dp)
                        ) {
                            Text(
                                text = "Today's Goals",
                                fontFamily = FontFamily.Serif,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                color = BloomTerracotta
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = viewModel.currentDate,
                                fontSize = 13.sp,
                                color = BloomTextSecondary
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Let's get blooming",
                                fontStyle = FontStyle.Italic,
                                fontSize = 13.sp,
                                color = BloomTextPrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "$completedCount of 5 complete",
                                fontSize = 12.sp,
                                color = BloomTextSecondary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            LinearProgressIndicator(
                                progress = { (completedCount / 5f).coerceIn(0f, 1f) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp)),
                                color = BloomTerracotta,
                                trackColor = BloomProgressInactive
                            )
                        }
                    }
                }
            }

            // 2. PCOS FAQ Carousel Card
            item {
                Column {
                    Text(
                        text = "PCOS FAQ",
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = BloomTextPrimary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    val faq = viewModel.faqList[activeFaqIndex % viewModel.faqList.size]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                activeFaqIndex = (activeFaqIndex + 1) % viewModel.faqList.size
                            },
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BloomBorder)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = faq.question,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp,
                                    color = BloomTextPrimary
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = faq.preview,
                                    fontSize = 13.sp,
                                    color = BloomTextSecondary,
                                    maxLines = 1
                                )
                            }
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = "View",
                                tint = BloomTextSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // Indicator Dots
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(viewModel.faqList.size) { index ->
                            val isActive = index == activeFaqIndex
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 3.dp)
                                    .size(if (isActive) 14.dp else 6.dp, 6.dp)
                                    .clip(CircleShape)
                                    .background(if (isActive) BloomTerracotta else BloomBorder)
                            )
                        }
                    }
                }
            }

            // 3. Quick Action Cards: Cycle & Symptom Check-in
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Cycle Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { activeSheet = HomeSheetType.CYCLE },
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
                                    contentDescription = "Cycle",
                                    tint = BloomTerracotta,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Cycle",
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

                    // Symptom Check-in Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { activeSheet = HomeSheetType.SYMPTOMS },
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
                                    .background(Color(0xFFFEF4E8)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.SentimentSatisfied,
                                    contentDescription = "Symptoms",
                                    tint = Color(0xFFC4822B),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Symptom check-in",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp,
                                    color = BloomTextPrimary
                                )
                                Text(
                                    text = "Bloating, skin, mood, sleep quality",
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

            // 4. Goal Progress List Section with Circular Progress Rings
            item {
                Column {
                    Text(
                        text = "Goal Progress",
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = BloomTextPrimary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        // Meals
                        GoalCard(
                            title = "Meals",
                            subtitle = "$mealsLogged/$mealsTarget meals",
                            progress = if (mealsTarget > 0) mealsLogged.toFloat() / mealsTarget else 0f,
                            color = BloomGoalMeals,
                            icon = Icons.Outlined.Restaurant,
                            onClick = { activeSheet = HomeSheetType.MEALS }
                        )

                        // Movement
                        GoalCard(
                            title = "Movement",
                            subtitle = "$movementLogged/$movementTarget min",
                            progress = if (movementTarget > 0) movementLogged.toFloat() / movementTarget else 0f,
                            color = BloomGoalMovement,
                            icon = Icons.AutoMirrored.Outlined.DirectionsWalk,
                            onClick = { activeSheet = HomeSheetType.MOVEMENT }
                        )

                        // Water
                        GoalCard(
                            title = "Water",
                            subtitle = "$waterLogged/$waterTarget glasses",
                            progress = if (waterTarget > 0) waterLogged.toFloat() / waterTarget else 0f,
                            color = BloomGoalWater,
                            icon = Icons.Outlined.WaterDrop,
                            onClick = { activeSheet = HomeSheetType.WATER }
                        )

                        // Relaxation
                        GoalCard(
                            title = "Relaxation",
                            subtitle = "$relaxationLogged/$relaxationTarget min",
                            progress = if (relaxationTarget > 0) relaxationLogged.toFloat() / relaxationTarget else 0f,
                            color = BloomGoalRelaxation,
                            icon = Icons.Outlined.Spa,
                            onClick = { activeSheet = HomeSheetType.RELAXATION }
                        )

                        // Sleep
                        GoalCard(
                            title = "Sleep",
                            subtitle = "$sleepLogged/$sleepTarget hrs",
                            progress = if (sleepTarget > 0) sleepLogged.toFloat() / sleepTarget else 0f,
                            color = BloomGoalSleep,
                            icon = Icons.Outlined.Bedtime,
                            onClick = { activeSheet = HomeSheetType.SLEEP }
                        )
                    }
                }
            }

            // 5. Today's Tip Card
            item {
                Column {
                    Text(
                        text = "Today's tip",
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
                        colors = CardDefaults.cardColors(containerColor = BloomTipBg),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEBDDCF))
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFEADBCE)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Bedtime,
                                        contentDescription = null,
                                        tint = BloomTipBrown,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "LOG LAST NIGHT'S SLEEP",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    letterSpacing = 1.sp,
                                    color = BloomTipBrown
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "You haven't logged sleep yet. Consistent, close-to-8-hr nights can help keep your hormones more balanced — avoiding phones and screens before bed makes it easier to fall asleep.",
                                fontSize = 13.sp,
                                lineHeight = 19.sp,
                                color = BloomTextPrimary
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Remind me later",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = BloomTextSecondary,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color(0xFFEADBCE))
                                        .clickable { /* Dismiss tip */ }
                                        .padding(horizontal = 14.dp, vertical = 8.dp)
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(if (isTipDone) BloomTipDoneGreen.copy(alpha = 0.8f) else BloomTipDoneGreen)
                                        .clickable { viewModel.markTipDone() }
                                        .padding(horizontal = 14.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (isTipDone) "Completed" else "Mark as done",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 6. AdMob Adaptive Banner Ad
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AdaptiveBannerAd(modifier = Modifier.fillMaxWidth())
                }
            }
        }

        // Interactive Bottom Sheets from Figma
        activeSheet?.let { sheet ->
            BloomBottomSheetContainer(
                onDismissRequest = { activeSheet = null }
            ) {
                when (sheet) {
                    HomeSheetType.MEALS -> LogMealsSheet(
                        onClose = { activeSheet = null },
                        onSave = { count -> viewModel.setMeals(count) }
                    )
                    HomeSheetType.MOVEMENT -> LogMovementSheet(
                        currentMinutes = movementLogged,
                        targetMinutes = movementTarget,
                        onClose = { activeSheet = null },
                        onSave = { min -> viewModel.setMovement(min) }
                    )
                    HomeSheetType.WATER -> LogWaterSheet(
                        currentGlasses = waterLogged,
                        targetGlasses = waterTarget,
                        onClose = { activeSheet = null },
                        onSave = { glasses -> viewModel.setWater(glasses) }
                    )
                    HomeSheetType.RELAXATION -> LogRelaxationSheet(
                        currentMinutes = relaxationLogged,
                        targetMinutes = relaxationTarget,
                        onClose = { activeSheet = null },
                        onSave = { min -> viewModel.setRelaxation(min) }
                    )
                    HomeSheetType.SLEEP -> LogSleepSheet(
                        currentHours = sleepLogged,
                        targetHours = sleepTarget,
                        onClose = { activeSheet = null },
                        onSave = { hrs -> viewModel.setSleep(hrs) }
                    )
                    HomeSheetType.CYCLE -> LogCycleSheet(
                        onClose = { activeSheet = null },
                        onSave = { /* Saved cycle */ }
                    )
                    HomeSheetType.SYMPTOMS -> TodaysSymptomsSheet(
                        onClose = { activeSheet = null },
                        onSave = { /* Saved symptoms */ }
                    )
                }
            }
        }
    }
}

@Composable
private fun GoalCard(
    title: String,
    subtitle: String,
    progress: Float,
    color: Color,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, BloomBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressRing(
                progress = progress,
                ringColor = color,
                size = 44.dp,
                strokeWidth = 3.5.dp
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = BloomTextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
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
