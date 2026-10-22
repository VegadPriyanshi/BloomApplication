package com.bloom.wellness.ads

/**
 * Centralized AdMob configuration and Google Test Ad Unit IDs.
 * These unit IDs are the official Google-provided test IDs for Android.
 */
object AdConstants {
    // AdMob App ID configured in AndroidManifest.xml:
    // ca-app-pub-3940256099942544~3347511713

    // Official Google Test App Open Ad Unit ID
    const val APP_OPEN_AD_UNIT_ID = "ca-app-pub-3940256099942544/9257395921"

    // Official Google Test Adaptive Banner Ad Unit ID
    const val BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"

    // Ad expiration time: 4 hours (as recommended in Google AdMob documentation)
    const val AD_EXPIRATION_HOURS = 4L
}
