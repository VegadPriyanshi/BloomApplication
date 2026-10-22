package com.bloom.wellness.ads

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowMetrics
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

/**
 * Reusable Jetpack Compose Adaptive Banner Ad Component.
 *
 * Dynamically computes adaptive banner height based on device orientation and container width.
 * Gracefully handles ad failure, logs analytics, and avoids content overlapping.
 */
@Composable
fun AdaptiveBannerAd(
    modifier: Modifier = Modifier,
    adUnitId: String = AdConstants.BANNER_AD_UNIT_ID
) {
    val context = LocalContext.current
    var isAdLoaded by remember { mutableStateOf(false) }
    var adFailedToLoad by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        val density = LocalDensity.current
        val widthPixels = with(density) { maxWidth.toPx().toInt() }
        val widthDp = (widthPixels / context.resources.displayMetrics.density).toInt()

        // Calculate anchored adaptive banner size
        val adSize = remember(widthDp) {
            val safeWidth = if (widthDp > 0) widthDp else 320
            AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, safeWidth)
        }

        val adView = remember(adUnitId, adSize) {
            AdView(context).apply {
                this.adUnitId = adUnitId
                setAdSize(adSize)
                adListener = object : AdListener() {
                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        isAdLoaded = true
                        adFailedToLoad = false
                        AdAnalytics.logEvent(AdAnalytics.EVENT_BANNER_AD_LOADED)
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        super.onAdFailedToLoad(loadAdError)
                        isAdLoaded = false
                        adFailedToLoad = true
                        AdAnalytics.logEvent(
                            AdAnalytics.EVENT_BANNER_AD_FAILED,
                            mapOf("code" to loadAdError.code, "message" to loadAdError.message)
                        )
                    }

                    override fun onAdImpression() {
                        super.onAdImpression()
                        AdAnalytics.logEvent(AdAnalytics.EVENT_BANNER_AD_IMPRESSION)
                    }
                }
                loadAd(AdRequest.Builder().build())
            }
        }

        DisposableEffect(adView) {
            onDispose {
                adView.destroy()
            }
        }

        if (adFailedToLoad) {
            // Elegant subtle placeholder when ad fails to load or offline
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Bloom Partner Showcase",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }
        } else {
            AndroidView(
                factory = { adView },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
