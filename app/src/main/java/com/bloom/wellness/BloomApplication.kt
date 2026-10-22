package com.bloom.wellness

import android.app.Application
import android.util.Log
import com.bloom.wellness.ads.AppOpenAdManager
import com.google.android.gms.ads.MobileAds

class BloomApplication : Application() {

    companion object {
        private const val TAG = "BloomApplication"
    }

    lateinit var appOpenAdManager: AppOpenAdManager
        private set

    override fun onCreate() {
        super.onCreate()

        // Initialize Lifecycle-Aware App Open Ad Manager
        appOpenAdManager = AppOpenAdManager(this)

        // Initialize Google Mobile Ads SDK on main thread as per AdMob guidelines
        MobileAds.initialize(this) { initializationStatus ->
            Log.d(TAG, "AdMob SDK Initialized: ${initializationStatus.adapterStatusMap}")
            // Preload the first App Open Ad only after SDK initialization completes
            appOpenAdManager.loadAd(this)
        }
    }
}
