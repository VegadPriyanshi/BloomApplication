package com.bloom.wellness.ui.splash

import android.app.Activity
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bloom.wellness.ads.AppOpenAdManager
import com.bloom.wellness.data.preferences.OnboardingPreferences
import com.bloom.wellness.ui.components.BloomFlowerGraphic
import com.bloom.wellness.ui.theme.BloomCreamBg
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull

/**
 * Exact Figma Splash Screen:
 * Clean cream canvas with centered Bloom Flower, radial glow, and delicate sparkles.
 */
@Composable
fun SplashScreen(
    onboardingPreferences: OnboardingPreferences,
    appOpenAdManager: AppOpenAdManager?,
    onNavigateToOnboarding: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity

    var startAnimation by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1.0f else 0.85f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "splash_flower_scale"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(1200)

        val isCompleted = try {
            withTimeoutOrNull(2000) {
                onboardingPreferences.isOnboardingCompleted.first()
            } ?: false
        } catch (_: Exception) {
            false
        }

        if (!isCompleted) {
            // First time user: never interrupted by App Open Ad
            appOpenAdManager?.isOnboardingInProgress = true
            onNavigateToOnboarding()
        } else {
            // Returning user: show App Open Ad if available
            appOpenAdManager?.isOnboardingInProgress = false
            if (activity != null && appOpenAdManager != null && appOpenAdManager.isAdAvailable()) {
                appOpenAdManager.showAdIfAvailable(activity) {
                    onNavigateToHome()
                }
            } else {
                onNavigateToHome()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomCreamBg),
        contentAlignment = Alignment.Center
    ) {
        BloomFlowerGraphic(
            modifier = Modifier.scale(scale),
            size = 220.dp,
            showSparkles = true,
            showGlow = true
        )
    }
}
