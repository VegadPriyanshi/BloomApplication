package com.bloom.wellness.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bloom.wellness.data.preferences.OnboardingPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class FaqItem(
    val id: String,
    val question: String,
    val preview: String,
    val fullAnswer: String
)

class HomeViewModel(
    private val onboardingPreferences: OnboardingPreferences
) : ViewModel() {

    val userName: StateFlow<String> = onboardingPreferences.userName.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        "Sofia"
    )

    // Current Date formatted e.g. "Saturday, June 27"
    val currentDate: String = SimpleDateFormat("EEEE, MMMM d", Locale.ENGLISH).format(Date())

    // Goal Logging State
    private val _mealsLogged = MutableStateFlow(0)
    val mealsLogged: StateFlow<Int> = _mealsLogged.asStateFlow()

    private val _movementLogged = MutableStateFlow(0)
    val movementLogged: StateFlow<Int> = _movementLogged.asStateFlow()

    private val _waterLogged = MutableStateFlow(0)
    val waterLogged: StateFlow<Int> = _waterLogged.asStateFlow()

    private val _relaxationLogged = MutableStateFlow(0)
    val relaxationLogged: StateFlow<Int> = _relaxationLogged.asStateFlow()

    private val _sleepLogged = MutableStateFlow(0)
    val sleepLogged: StateFlow<Int> = _sleepLogged.asStateFlow()

    // Targets from preferences
    val mealsTarget: StateFlow<Int> = onboardingPreferences.mealsTarget.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), 3
    )
    val movementTarget: StateFlow<Int> = onboardingPreferences.movementTarget.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), 30
    )
    val waterTarget: StateFlow<Int> = onboardingPreferences.waterTarget.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), 8
    )
    val relaxationTarget: StateFlow<Int> = onboardingPreferences.relaxationTarget.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), 15
    )
    val sleepTarget: StateFlow<Int> = onboardingPreferences.sleepTarget.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), 8
    )

    // Completed goals count out of 5
    val completedGoalsCount: StateFlow<Int> = combine(
        mealsLogged, mealsTarget,
        movementLogged, movementTarget,
        waterLogged, waterTarget,
        relaxationLogged, relaxationTarget,
        sleepLogged, sleepTarget
    ) { values ->
        val mLogged = values[0] as Int
        val mTarget = values[1] as Int
        val movLogged = values[2] as Int
        val movTarget = values[3] as Int
        val wLogged = values[4] as Int
        val wTarget = values[5] as Int
        val rLogged = values[6] as Int
        val rTarget = values[7] as Int
        val sLogged = values[8] as Int
        val sTarget = values[9] as Int

        var count = 0
        if (mLogged >= mTarget && mTarget > 0) count++
        if (movLogged >= movTarget && movTarget > 0) count++
        if (wLogged >= wTarget && wTarget > 0) count++
        if (rLogged >= rTarget && rTarget > 0) count++
        if (sLogged >= sTarget && sTarget > 0) count++
        count
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Today's tip state
    private val _isTipDone = MutableStateFlow(false)
    val isTipDone: StateFlow<Boolean> = _isTipDone.asStateFlow()

    // FAQ items from Figma
    val faqList = listOf(
        FaqItem(
            id = "1",
            question = "What exactly is PCOS?",
            preview = "PCOS stands for polycystic ovary syndrome...",
            fullAnswer = "Polycystic ovary syndrome (PCOS) is a common hormonal condition affecting women of reproductive age. It is characterized by irregular periods, excess androgen (male hormone) levels, and sometimes enlarged ovaries with small follicles."
        ),
        FaqItem(
            id = "2",
            question = "What causes PCOS?",
            preview = "Genetics, insulin resistance, and inflammation...",
            fullAnswer = "The exact cause isn't fully understood, but genetics, insulin resistance, and low-grade inflammation play major roles in PCOS pathogenesis."
        ),
        FaqItem(
            id = "3",
            question = "Can food really make a difference?",
            preview = "Balancing blood sugar is the most effective lever...",
            fullAnswer = "Yes! Whole foods, complex carbs paired with protein and healthy fats prevent insulin spikes, soothing androgen production and improving ovulatory health."
        )
    )

    fun incrementMeals() {
        _mealsLogged.value = (_mealsLogged.value + 1).coerceAtMost(mealsTarget.value)
    }

    fun incrementMovement() {
        _movementLogged.value = (_movementLogged.value + 10).coerceAtMost(movementTarget.value)
    }

    fun incrementWater() {
        _waterLogged.value = (_waterLogged.value + 1).coerceAtMost(waterTarget.value)
    }

    fun incrementRelaxation() {
        _relaxationLogged.value = (_relaxationLogged.value + 5).coerceAtMost(relaxationTarget.value)
    }

    fun incrementSleep() {
        _sleepLogged.value = (_sleepLogged.value + 1).coerceAtMost(sleepTarget.value)
    }

    fun setMeals(count: Int) {
        _mealsLogged.value = count
    }

    fun setMovement(min: Int) {
        _movementLogged.value = min
    }

    fun setWater(glasses: Int) {
        _waterLogged.value = glasses
    }

    fun setRelaxation(min: Int) {
        _relaxationLogged.value = min
    }

    fun setSleep(hrs: Int) {
        _sleepLogged.value = hrs
    }

    fun markTipDone() {
        _isTipDone.value = true
        _sleepLogged.value = sleepTarget.value
    }
}
