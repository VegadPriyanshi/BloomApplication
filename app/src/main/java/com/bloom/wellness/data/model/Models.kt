package com.bloom.wellness.data.model

import androidx.compose.ui.graphics.Color

/**
 * Onboarding Slide Data Model
 */
data class OnboardingPage(
    val title: String,
    val subtitle: String,
    val description: String,
    val badge: String
)

/**
 * Menstrual Cycle Phases
 */
enum class MenstrualPhase(
    val displayName: String,
    val description: String,
    val hormoneHighlights: String,
    val colorHex: Long
) {
    MENSTRUAL(
        displayName = "Menstrual Phase",
        description = "Rest and restore. Gentle movement and nourishing foods are best.",
        hormoneHighlights = "Estrogen & Progesterone are at their lowest.",
        colorHex = 0xFFE03E6C
    ),
    FOLLICULAR(
        displayName = "Follicular Phase",
        description = "Energy is rising. Great time for new projects and creative focus.",
        hormoneHighlights = "Estrogen is steadily rising, boosting mood & stamina.",
        colorHex = 0xFF8E44AD
    ),
    OVULATORY(
        displayName = "Ovulatory Phase",
        description = "Peak energy and confidence. Fertility window is highest.",
        hormoneHighlights = "Estrogen peaks and LH surges to release an egg.",
        colorHex = 0xFF27AE60
    ),
    LUTEAL(
        displayName = "Luteal Phase",
        description = "Wind down and practice self-care. Manage PCOS & PMS symptoms.",
        hormoneHighlights = "Progesterone peaks, then drops if no pregnancy occurs.",
        colorHex = 0xFFE67E22
    )
}

/**
 * Cycle State Model
 */
data class CycleInfo(
    val currentDay: Int = 12,
    val cycleLength: Int = 28,
    val periodLength: Int = 5,
    val phase: MenstrualPhase = MenstrualPhase.FOLLICULAR,
    val daysUntilNextPeriod: Int = 16,
    val chanceOfPregnancy: String = "Medium Chance"
)

/**
 * Symptom Types with icon labels
 */
enum class SymptomType(val displayName: String, val icon: String) {
    CRAMPS("Cramps", "⚡"),
    BLOATING("Bloating", "🎈"),
    HEADACHE("Headache", "🤕"),
    ACNE("Acne", "✨"),
    FATIGUE("Fatigue", "😴"),
    MOOD_SWINGS("Mood Swings", "🌊"),
    CRAVINGS("Cravings", "🍫"),
    BACKACHE("Backache", "🩹")
}

/**
 * Mood Types
 */
enum class MoodType(val displayName: String, val emoji: String) {
    HAPPY("Happy", "😊"),
    CALM("Calm", "😌"),
    ENERGETIC("Energetic", "⚡"),
    TIRED("Tired", "😴"),
    ANXIOUS("Anxious", "🥺")
}

/**
 * Energy Levels
 */
enum class EnergyLevel(val displayName: String) {
    HIGH("High"),
    MODERATE("Moderate"),
    LOW("Low")
}

/**
 * Health Insights and PCOS Articles
 */
data class HealthInsight(
    val id: String,
    val title: String,
    val category: String,
    val readTime: String,
    val description: String,
    val tag: String
)
