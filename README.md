# README

## Setup instructions
- Prerequisites (Android Studio, JDK 17/21, SDK 24-35)
- Steps to Run in Android Studio
- Command-Line Build & Installation (assembleDebug, adb install, adb am start)

## Architecture used
- MVVM (Model-View-ViewModel) + Clean Architecture
- UI Layer: 100% Jetpack Compose + Material 3 Design System
- 10 Figma Interactive ModalBottomSheets
- Navigation: Jetpack Navigation (Splash ➔ Onboarding ➔ Main)
- State & Data: Kotlin Coroutines, StateFlow, Jetpack DataStore Preferences

## Ad implementation approach
- Google Mobile Ads SDK (v23.6.0)
- App Open Ad (AppOpenAdManager with ProcessLifecycleOwner & Onboarding UX protection)
- Adaptive Banner Ad (Anchored adaptive height in Dashboard)
- Ad Analytics Event Tracking (AdAnalytics)

## Test Ad IDs used
- AdMob Application ID: ca-app-pub-3940256099942544~3347511713
- App Open Ad Unit ID: ca-app-pub-3940256099942544/9257395921
- Adaptive Banner Ad Unit ID: ca-app-pub-3940256099942544/6300978111

## Assumptions/limitations
- Assumptions: Android 7.0 (API 24) to Android 15 (API 35), Offline Local DataStore, Portrait orientation.
- Limitations: Test Ad creative fetching requires network connection, Single-device scope (no cloud sync).