package com.bloom.wellness.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bloom.wellness.ads.AppOpenAdManager
import com.bloom.wellness.data.preferences.OnboardingPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TrackingGoalItem(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val isEnabled: Boolean = true
)

data class ReminderOption(
    val id: String,
    val title: String,
    val subtitle: String,
    val time: String
)

class OnboardingViewModel(
    private val onboardingPreferences: OnboardingPreferences,
    private val appOpenAdManager: AppOpenAdManager? = null
) : ViewModel() {

    private val _currentStep = MutableStateFlow(1)
    val currentStep: StateFlow<Int> = _currentStep.asStateFlow()

    private val _userName = MutableStateFlow("Sofia")
    val userName: StateFlow<String> = _userName.asStateFlow()

    val reminderOptions = listOf(
        ReminderOption("morning", "Morning", "7:30 AM — start the day on track", "07:30 AM"),
        ReminderOption("afternoon", "Afternoon", "1:00 PM — a midday check-in", "01:00 PM"),
        ReminderOption("evening", "Evening", "8:00 PM — wind down and reflect", "08:00 PM")
    )

    private val _selectedReminderId = MutableStateFlow("morning")
    val selectedReminderId: StateFlow<String> = _selectedReminderId.asStateFlow()

    private val _customTime = MutableStateFlow("08:00 AM")
    val customTime: StateFlow<String> = _customTime.asStateFlow()

    private val _goals = MutableStateFlow(
        listOf(
            TrackingGoalItem(
                id = "meals",
                title = "Meals • 3 balanced meals",
                description = "Steadier blood sugar helps keep PCOS symptoms in check.",
                iconEmoji = "🍴",
                isEnabled = true
            ),
            TrackingGoalItem(
                id = "movement",
                title = "Movement • 30 minutes",
                description = "Supports insulin sensitivity, a key lever in PCOS.",
                iconEmoji = "🚶‍♀️",
                isEnabled = true
            ),
            TrackingGoalItem(
                id = "water",
                title = "Water • 8 glasses",
                description = "Helps with energy and bloating.",
                iconEmoji = "💧",
                isEnabled = true
            ),
            TrackingGoalItem(
                id = "relaxation",
                title = "Relaxation • 15 minutes",
                description = "Lower stress means lower cortisol, which can worsen symptoms.",
                iconEmoji = "🍃",
                isEnabled = true
            ),
            TrackingGoalItem(
                id = "sleep",
                title = "Sleep • 8 hours",
                description = "Consistent sleep helps balance hormones over time.",
                iconEmoji = "🌙",
                isEnabled = true
            ),
            TrackingGoalItem(
                id = "cycle",
                title = "Cycle • period dates",
                description = "Spot your personal pattern and predict your next period.",
                iconEmoji = "🩸",
                isEnabled = true
            ),
            TrackingGoalItem(
                id = "symptoms",
                title = "Symptom check-in • bloating, skin, mood, sleep quality",
                description = "Track how you feel day to day to notice patterns over time.",
                iconEmoji = "😊",
                isEnabled = true
            )
        )
    )
    val goals: StateFlow<List<TrackingGoalItem>> = _goals.asStateFlow()

    init {
        // Guard: App Open Ad must never interrupt onboarding
        appOpenAdManager?.isOnboardingInProgress = true
    }

    fun setUserName(name: String) {
        _userName.value = name
    }

    fun toggleGoal(id: String) {
        _goals.value = _goals.value.map { item ->
            if (item.id == id) item.copy(isEnabled = !item.isEnabled) else item
        }
    }

    fun selectReminder(id: String) {
        _selectedReminderId.value = id
    }

    fun setCustomTime(time: String) {
        _customTime.value = time
        _selectedReminderId.value = "custom"
    }

    fun nextStep(onComplete: () -> Unit) {
        if (_currentStep.value < 4) {
            _currentStep.value += 1
        } else {
            completeOnboarding(onComplete)
        }
    }

    fun previousStep() {
        if (_currentStep.value > 1) {
            _currentStep.value -= 1
        }
    }

    fun completeOnboarding(onFinished: () -> Unit) {
        viewModelScope.launch {
            val chosenTime = when (_selectedReminderId.value) {
                "morning" -> "07:30 AM"
                "afternoon" -> "01:00 PM"
                "evening" -> "08:00 PM"
                else -> _customTime.value
            }
            onboardingPreferences.setUserName(_userName.value.ifBlank { "Sofia" })
            onboardingPreferences.setReminderTime(chosenTime)
            onboardingPreferences.setOnboardingCompleted(true)
            appOpenAdManager?.isOnboardingInProgress = false
            onFinished()
        }
    }
}
