package com.bloom.wellness.ads

import android.util.Log

/**
 * Basic Ad Event Analytics Tracker (Bonus Requirement).
 * Tracks lifecycle events for App Open Ads and Banner Ads.
 */
object AdAnalytics {
    private const val TAG = "BloomAdAnalytics"

    // App Open Ad Events
    const val EVENT_APP_OPEN_AD_REQUESTED = "app_open_ad_requested"
    const val EVENT_APP_OPEN_AD_LOADED = "app_open_ad_loaded"
    const val EVENT_APP_OPEN_AD_FAILED = "app_open_ad_failed"
    const val EVENT_APP_OPEN_AD_SHOWN = "app_open_ad_shown"
    const val EVENT_APP_OPEN_AD_DISMISSED = "app_open_ad_dismissed"

    // Banner Ad Events
    const val EVENT_BANNER_AD_LOADED = "banner_ad_loaded"
    const val EVENT_BANNER_AD_FAILED = "banner_ad_failed"
    const val EVENT_BANNER_AD_IMPRESSION = "banner_ad_impression"

    fun logEvent(eventName: String, params: Map<String, Any?> = emptyMap()) {
        val details = if (params.isNotEmpty()) {
            " with params: " + params.entries.joinToString { "${it.key}=${it.value}" }
        } else ""
        Log.d(TAG, "Ad Event: [$eventName]$details")
    }
}
