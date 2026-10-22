package com.bloom.wellness.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bloom.wellness.ui.theme.BloomGold
import com.bloom.wellness.ui.theme.BloomTerracotta

/**
 * Exact Flower Graphic matching Figma Logo, Splash & Onboarding.
 * Renders 4 terracotta petals, gold center, soft radial glow and sparkles.
 */
@Composable
fun BloomFlowerGraphic(
    modifier: Modifier = Modifier,
    size: Dp = 180.dp,
    showSparkles: Boolean = true,
    showGlow: Boolean = true
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val center = Offset(this.size.width / 2f, this.size.height / 2f)
            val baseRadius = this.size.minDimension / 2f

            // 1. Soft radial glow
            if (showGlow) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x33A2584D),
                            Color(0x15A2584D),
                            Color.Transparent
                        ),
                        center = center,
                        radius = baseRadius * 1.05f
                    ),
                    radius = baseRadius * 1.05f,
                    center = center
                )
            }

            // Dimensions for petals
            val petalWidth = baseRadius * 0.44f
            val petalLength = baseRadius * 0.62f
            val centerRadius = baseRadius * 0.23f

            // 2. Draw 4 Petals
            // Top petal
            drawOval(
                color = BloomTerracotta,
                topLeft = Offset(center.x - petalWidth / 2f, center.y - petalLength),
                size = Size(petalWidth, petalLength)
            )
            // Bottom petal
            drawOval(
                color = BloomTerracotta,
                topLeft = Offset(center.x - petalWidth / 2f, center.y),
                size = Size(petalWidth, petalLength)
            )
            // Left petal
            drawOval(
                color = BloomTerracotta,
                topLeft = Offset(center.x - petalLength, center.y - petalWidth / 2f),
                size = Size(petalLength, petalWidth)
            )
            // Right petal
            drawOval(
                color = BloomTerracotta,
                topLeft = Offset(center.x, center.y - petalWidth / 2f),
                size = Size(petalLength, petalWidth)
            )

            // 3. Gold center circle
            drawCircle(
                color = BloomGold,
                radius = centerRadius,
                center = center
            )

            // 4. Delicate Sparkles (4-pointed stars in gold)
            if (showSparkles) {
                // Top-Left sparkle
                drawSparkle(
                    center = Offset(this.size.width * 0.18f, this.size.height * 0.20f),
                    size = baseRadius * 0.11f,
                    color = BloomGold
                )
                // Top-Right sparkle (smaller)
                drawSparkle(
                    center = Offset(this.size.width * 0.85f, this.size.height * 0.26f),
                    size = baseRadius * 0.08f,
                    color = Color(0xFFD4A77E)
                )
                // Bottom-Left sparkle
                drawSparkle(
                    center = Offset(this.size.width * 0.16f, this.size.height * 0.80f),
                    size = baseRadius * 0.09f,
                    color = BloomGold
                )
            }
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawSparkle(
    center: Offset,
    size: Float,
    color: Color
) {
    val path = Path().apply {
        moveTo(center.x, center.y - size)
        quadraticTo(center.x, center.y, center.x + size, center.y)
        quadraticTo(center.x, center.y, center.x, center.y + size)
        quadraticTo(center.x, center.y, center.x - size, center.y)
        quadraticTo(center.x, center.y, center.x, center.y - size)
        close()
    }
    drawPath(path = path, color = color)
}
