package com.bloom.wellness.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bloom.wellness.BloomApplication
import com.bloom.wellness.data.preferences.OnboardingPreferences
import com.bloom.wellness.ui.home.HomeViewModel
import com.bloom.wellness.ui.main.MainScreen
import com.bloom.wellness.ui.onboarding.OnboardingScreen
import com.bloom.wellness.ui.onboarding.OnboardingViewModel
import com.bloom.wellness.ui.splash.SplashScreen

@Composable
fun BloomNavHost(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val app = context.applicationContext as? BloomApplication
    val appOpenAdManager = app?.appOpenAdManager

    val onboardingPreferences = remember { OnboardingPreferences(context) }
    val onboardingViewModel = remember {
        OnboardingViewModel(
            onboardingPreferences = onboardingPreferences,
            appOpenAdManager = appOpenAdManager
        )
    }
    val homeViewModel = remember {
        HomeViewModel(onboardingPreferences = onboardingPreferences)
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onboardingPreferences = onboardingPreferences,
                appOpenAdManager = appOpenAdManager,
                onNavigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                viewModel = onboardingViewModel,
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            MainScreen(
                homeViewModel = homeViewModel,
                preferences = onboardingPreferences,
                onSignOut = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
