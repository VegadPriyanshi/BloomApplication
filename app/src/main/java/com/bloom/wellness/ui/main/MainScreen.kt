package com.bloom.wellness.ui.main

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bloom.wellness.data.preferences.OnboardingPreferences
import com.bloom.wellness.ui.home.HomeScreen
import com.bloom.wellness.ui.home.HomeViewModel
import com.bloom.wellness.ui.insights.InsightsScreen
import com.bloom.wellness.ui.learn.LearnScreen
import com.bloom.wellness.ui.settings.SettingsScreen
import com.bloom.wellness.ui.theme.BloomBorder
import com.bloom.wellness.ui.theme.BloomCreamBg
import com.bloom.wellness.ui.theme.BloomTerracotta
import com.bloom.wellness.ui.theme.BloomTextSecondary

enum class MainTab(val title: String, val icon: ImageVector) {
    DASHBOARD("Dashboard", Icons.Outlined.Home),
    INSIGHTS("Insights", Icons.Outlined.BarChart),
    LEARN("Learn", Icons.Outlined.AutoStories),
    SETTINGS("Settings", Icons.Outlined.Settings)
}

/**
 * Main application host container with bottom navigation bar matching Figma design.
 */
@Composable
fun MainScreen(
    homeViewModel: HomeViewModel,
    preferences: OnboardingPreferences,
    onSignOut: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = remember { MainTab.entries }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomCreamBg)
    ) {
        // Tab Content
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Crossfade(
                targetState = selectedTab,
                label = "main_screen_tab_crossfade"
            ) { tabIndex ->
                when (tabs[tabIndex]) {
                    MainTab.DASHBOARD -> HomeScreen(
                        viewModel = homeViewModel,
                        onNavigateToSettings = { selectedTab = 3 },
                        onNavigateToLearn = { selectedTab = 2 }
                    )
                    MainTab.INSIGHTS -> InsightsScreen()
                    MainTab.LEARN -> LearnScreen()
                    MainTab.SETTINGS -> SettingsScreen(
                        preferences = preferences,
                        onSignOut = onSignOut
                    )
                }
            }
        }

        // Bottom Navigation Bar matching Figma
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .navigationBarsPadding()
        ) {
            HorizontalDivider(
                color = BloomBorder.copy(alpha = 0.8f),
                thickness = 0.8.dp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                tabs.forEachIndexed { index, tab ->
                    val isSelected = selectedTab == index
                    val tint = if (isSelected) BloomTerracotta else BloomTextSecondary

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedTab = index }
                            .padding(vertical = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.title,
                            tint = tint,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = tab.title,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = tint
                        )
                    }
                }
            }
        }
    }
}
