package com.bloom.wellness.ads

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import java.util.Date
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Manages Google AdMob App Open Ads with lifecycle awareness.
 *
 * Handles:
 * - Preloading and caching ads
 * - Expiration check (4 hours validity window)
 * - Safe presentation: never blocks user or shows during first-time onboarding
 * - Activity lifecycle tracking & foreground events (ProcessLifecycleOwner)
 * - Graceful failure handling and automatic reloading
 * - Analytics event tracking
 */
class AppOpenAdManager(
    private val application: Application
) : Application.ActivityLifecycleCallbacks, DefaultLifecycleObserver {

    companion object {
        private const val TAG = "AppOpenAdManager"
    }

    private var appOpenAd: AppOpenAd? = null
    private var isLoadingAd = false
    var isShowingAd = false
        private set

    private var loadTime: Long = 0
    private var currentActivity: Activity? = null

    /**
     * Flag indicating whether the user is eligible for App Open Ads.
     * Set to false while the user is completing onboarding for the first time.
     */
    var isOnboardingInProgress: Boolean = false

    init {
        application.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    /**
     * Preloads an App Open Ad if one is not already available or loading.
     */
    fun loadAd(context: Context) {
        if (isLoadingAd || isAdAvailable()) {
            return
        }

        isLoadingAd = true
        AdAnalytics.logEvent(AdAnalytics.EVENT_APP_OPEN_AD_REQUESTED)

        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            context,
            AdConstants.APP_OPEN_AD_UNIT_ID,
            request,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    Log.d(TAG, "App Open Ad loaded successfully.")
                    appOpenAd = ad
                    isLoadingAd = false
                    loadTime = Date().time
                    AdAnalytics.logEvent(AdAnalytics.EVENT_APP_OPEN_AD_LOADED)
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.w(TAG, "App Open Ad failed to load: ${loadAdError.message} (code: ${loadAdError.code})")
                    isLoadingAd = false
                    appOpenAd = null
                    AdAnalytics.logEvent(
                        AdAnalytics.EVENT_APP_OPEN_AD_FAILED,
                        mapOf("code" to loadAdError.code, "message" to loadAdError.message)
                    )
                }
            }
        )
    }

    /**
     * Checks if cached ad was loaded within the specified validity window.
     */
    private fun wasLoadTimeLessThanNHoursAgo(numHours: Long): Boolean {
        val dateDifference = Date().time - loadTime
        val numMilliSecondsPerHour = 3600000
        return dateDifference < (numMilliSecondsPerHour * numHours)
    }

    /**
     * Returns true if a valid, non-expired ad is ready to show.
     */
    fun isAdAvailable(): Boolean {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(AdConstants.AD_EXPIRATION_HOURS)
    }

    /**
     * Shows the App Open Ad if available and eligible.
     *
     * @param activity The host activity in which to display the ad.
     * @param onComplete Callback invoked when the ad completes, fails to show, or is not available.
     *                   Ensures the user flow is NEVER blocked.
     */
    fun showAdIfAvailable(
        activity: Activity,
        onComplete: () -> Unit = {}
    ) {
        // Guard 1: Never interrupt first-time onboarding
        if (isOnboardingInProgress) {
            Log.d(TAG, "User is in onboarding flow. Skipping App Open Ad.")
            onComplete()
            return
        }

        // Guard 2: Prevent concurrent ad displays
        if (isShowingAd) {
            Log.d(TAG, "An App Open Ad is already showing.")
            onComplete()
            return
        }

        // Guard 3: Check ad availability and validity
        if (!isAdAvailable()) {
            Log.d(TAG, "No App Open Ad available to display. Continuing to app.")
            onComplete()
            loadAd(activity)
            return
        }

        val ad = appOpenAd ?: run {
            onComplete()
            return
        }

        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                isShowingAd = true
                Log.d(TAG, "App Open Ad showed full screen content.")
                AdAnalytics.logEvent(AdAnalytics.EVENT_APP_OPEN_AD_SHOWN)
            }

            override fun onAdDismissedFullScreenContent() {
                appOpenAd = null
                isShowingAd = false
                Log.d(TAG, "App Open Ad dismissed.")
                AdAnalytics.logEvent(AdAnalytics.EVENT_APP_OPEN_AD_DISMISSED)
                onComplete()
                // Preload next ad for future foreground events
                loadAd(activity)
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                appOpenAd = null
                isShowingAd = false
                Log.w(TAG, "App Open Ad failed to show: ${adError.message}")
                AdAnalytics.logEvent(
                    AdAnalytics.EVENT_APP_OPEN_AD_FAILED,
                    mapOf("error" to adError.message)
                )
                onComplete()
                // Preload next ad
                loadAd(activity)
            }
        }

        ad.show(activity)
    }

    // DefaultLifecycleObserver: foreground trigger
    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.d(TAG, "App moved to foreground (onStart).")
        currentActivity?.let { activity ->
            // Display ad on returning foreground only if eligible
            if (!isOnboardingInProgress && !isShowingAd) {
                showAdIfAvailable(activity)
            }
        }
    }

    // ActivityLifecycleCallbacks
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {
        if (!isShowingAd) {
            currentActivity = activity
        }
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {
        if (currentActivity == activity) {
            currentActivity = null
        }
    }
}
