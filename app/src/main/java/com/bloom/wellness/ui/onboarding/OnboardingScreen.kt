package com.bloom.wellness.ui.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bloom.wellness.ui.components.BloomFlowerGraphic
import com.bloom.wellness.ui.theme.BloomBorder
import com.bloom.wellness.ui.theme.BloomCreamBg
import com.bloom.wellness.ui.theme.BloomProgressInactive
import com.bloom.wellness.ui.theme.BloomTerracotta
import com.bloom.wellness.ui.theme.BloomTerracottaLight
import com.bloom.wellness.ui.theme.BloomTextPrimary
import com.bloom.wellness.ui.theme.BloomTextSecondary
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.imePadding

/**
 * Exact Figma Onboarding Screen with Steps 1, 2, 3, and 4.
 */
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel,
    onNavigateToHome: () -> Unit
) {
    val currentStep by viewModel.currentStep.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val goals by viewModel.goals.collectAsState()
    val selectedReminderId by viewModel.selectedReminderId.collectAsState()
    val customTime by viewModel.customTime.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomCreamBg)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    )  {
        // Top Navigation Bar: Back button (on steps > 1) + 4-segment progress bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (currentStep > 1) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(BloomTerracottaLight.copy(alpha = 0.6f))
                        .clickable { viewModel.previousStep() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = BloomTerracotta,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
            }

            // 4-Segment Progress Bar
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (segment in 1..4) {
                    val isActive = segment <= currentStep
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(
                                if (isActive) BloomTerracotta else BloomProgressInactive
                            )
                    )
                }
            }
        }

        // Animated Screen Content according to Step
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            AnimatedContent(
                targetState = currentStep,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally { width -> width } togetherWith
                                slideOutHorizontally { width -> -width }
                    } else {
                        slideInHorizontally { width -> -width } togetherWith
                                slideOutHorizontally { width -> width }
                    }
                },
                label = "onboarding_step_transition"
            ) { step ->
                when (step) {
                    1 -> OnboardingStep1(
                        onGetStarted = { viewModel.nextStep(onNavigateToHome) }
                    )
                    2 -> OnboardingStep2(
                        userName = userName,
                        onNameChanged = { viewModel.setUserName(it) },
                        onContinue = { viewModel.nextStep(onNavigateToHome) }
                    )
                    3 -> OnboardingStep3(
                        goals = goals,
                        onToggleGoal = { viewModel.toggleGoal(it) },
                        onContinue = { viewModel.nextStep(onNavigateToHome) }
                    )
                    4 -> OnboardingStep4(
                        options = viewModel.reminderOptions,
                        selectedId = selectedReminderId,
                        customTime = customTime,
                        onSelectOption = { viewModel.selectReminder(it) },
                        onEnterBloom = { viewModel.completeOnboarding(onNavigateToHome) },
                        onLater = { viewModel.completeOnboarding(onNavigateToHome) }
                    )
                    else -> OnboardingStep1(
                        onGetStarted = { viewModel.nextStep(onNavigateToHome) }
                    )
                }
            }
        }
    }
}

/**
 * Step 1: Welcome to Bloom
 */
@Composable
private fun OnboardingStep1(
    onGetStarted: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Center Floral Graphic
        BloomFlowerGraphic(
            size = 220.dp,
            showSparkles = true,
            showGlow = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Welcome to Bloom",
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = BloomTextPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "A simple daily companion for managing PCOS — track your cycle, meals, movement, water, relaxation and sleep.",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 15.sp,
                lineHeight = 22.sp,
                color = BloomTextSecondary,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onGetStarted,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BloomTerracotta,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Get started",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

/**
 * Step 2: A little about you (Name Input)
 */
@Composable
private fun OnboardingStep2(
    userName: String,
    onNameChanged: (String) -> Unit,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        BloomFlowerGraphic(
            size = 48.dp,
            showSparkles = false,
            showGlow = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "A little about you",
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = BloomTextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "What should we call you? We'll use this to\npersonalize your daily check-in.",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = BloomTextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(36.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "YOUR FIRST NAME",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = BloomTextPrimary,
                modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            )

            OutlinedTextField(
                value = userName,
                onValueChange = onNameChanged,
                placeholder = {
                    Text(
                        text = "e.g., Sarah",
                        color = BloomTextSecondary.copy(alpha = 0.6f)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BloomTerracotta,
                    unfocusedBorderColor = BloomBorder,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = BloomTextPrimary,
                    unfocusedTextColor = BloomTextPrimary
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        val isNameEntered = userName.trim().isNotEmpty()
        Button(
            onClick = onContinue,
            enabled = isNameEntered,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BloomTerracotta,
                contentColor = Color.White,
                disabledContainerColor = BloomTerracottaLight,
                disabledContentColor = Color.White.copy(alpha = 0.8f)
            )
        ) {
            Text(
                text = "Continue",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

/**
 * Step 3: What would you like to track daily? (Goals & Switches)
 */
@Composable
private fun OnboardingStep3(
    goals: List<TrackingGoalItem>,
    onToggleGoal: (String) -> Unit,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                BloomFlowerGraphic(
                    size = 36.dp,
                    showSparkles = false,
                    showGlow = false
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "What would you like to track\ndaily?",
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    lineHeight = 30.sp,
                    color = BloomTextPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Pick the goals that matter to you — only these will show up on your dashboard. Partial progress still counts, and your flower fills in as you go.",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = BloomTextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(18.dp))
            }

            // White Rounded Card with 7 Goals
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp)
                ) {
                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                        goals.forEachIndexed { index, item ->
                            GoalRowItem(
                                item = item,
                                onToggle = { onToggleGoal(item.id) }
                            )
                            if (index < goals.size - 1) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    color = BloomBorder.copy(alpha = 0.7f),
                                    thickness = 0.8.dp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Targets and what you track can be adjusted any time in Settings.",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp,
                    color = BloomTextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BloomTerracotta,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Continue",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

/**
 * Step 4: When should we remind you? (Figma Image 1)
 */
@Composable
private fun OnboardingStep4(
    options: List<ReminderOption>,
    selectedId: String,
    customTime: String,
    onSelectOption: (String) -> Unit,
    onEnterBloom: () -> Unit,
    onLater: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Circular Clock Icon in Terracotta
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .border(1.5.dp, BloomTerracotta, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Schedule,
                contentDescription = "Clock",
                tint = BloomTerracotta,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "When should we remind you?",
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            color = BloomTextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We'll send one daily nudge to log how you're doing.",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp,
            color = BloomTextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Section: Pick a time of day
        Text(
            text = "Pick a time of day",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = BloomTextPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, bottom = 8.dp)
        )

        // 3 Reminder Radio Cards
        options.forEach { option ->
            val isSelected = selectedId == option.id
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onSelectOption(option.id) },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(
                    width = if (isSelected) 1.5.dp else 1.dp,
                    color = if (isSelected) BloomTerracotta else BloomBorder
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Custom Radio Circle
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .border(
                                width = if (isSelected) 5.dp else 1.5.dp,
                                color = if (isSelected) BloomTerracotta else BloomTextSecondary.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Text(
                            text = option.title,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            color = BloomTextPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = option.subtitle,
                            fontSize = 13.sp,
                            color = BloomTextSecondary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Section: Or set a custom time
        Text(
            text = "Or set a custom time",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = BloomTextPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, bottom = 8.dp)
        )

        val isCustomSelected = selectedId == "custom"
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSelectOption("custom") },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(
                width = if (isCustomSelected) 1.5.dp else 1.dp,
                color = if (isCustomSelected) BloomTerracotta else BloomBorder
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
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = BloomTextPrimary
                )

                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = "Select Time",
                    tint = BloomTextSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Enter Bloom Primary Button
        Button(
            onClick = onEnterBloom,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BloomTerracotta,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Enter Bloom",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Text Link: I'll set this up later
        Text(
            text = "I'll set this up later",
            fontSize = 14.sp,
            color = BloomTextSecondary,
            modifier = Modifier
                .clickable { onLater() }
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun GoalRowItem(
    item: TrackingGoalItem,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = BloomTextPrimary
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                color = BloomTextSecondary
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Switch(
            checked = item.isEnabled,
            onCheckedChange = { onToggle() },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = BloomTerracotta,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = BloomBorder
            )
        )
    }
}
