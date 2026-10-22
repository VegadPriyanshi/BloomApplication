package com.bloom.wellness.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "bloom_preferences")

class OnboardingPreferences(private val context: Context) {

    companion object {
        private val KEY_ONBOARDING_COMPLETED = booleanPreferencesKey("is_onboarding_completed")
        private val KEY_USER_NAME = stringPreferencesKey("user_name")
        private val KEY_REMINDER_TIME = stringPreferencesKey("reminder_time")
        private val KEY_NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        
        private val KEY_TARGET_MEALS = intPreferencesKey("target_meals")
        private val KEY_TARGET_MOVEMENT = intPreferencesKey("target_movement")
        private val KEY_TARGET_WATER = intPreferencesKey("target_water")
        private val KEY_TARGET_RELAXATION = intPreferencesKey("target_relaxation")
        private val KEY_TARGET_SLEEP = intPreferencesKey("target_sleep")
    }

    val isOnboardingCompleted: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[KEY_ONBOARDING_COMPLETED] ?: false
    }

    val userName: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[KEY_USER_NAME] ?: "Sofia"
    }

    val reminderTime: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[KEY_REMINDER_TIME] ?: "08:00 AM"
    }

    val notificationsEnabled: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[KEY_NOTIFICATIONS_ENABLED] ?: true
    }

    val mealsTarget: Flow<Int> = context.dataStore.data.map { it[KEY_TARGET_MEALS] ?: 3 }
    val movementTarget: Flow<Int> = context.dataStore.data.map { it[KEY_TARGET_MOVEMENT] ?: 30 }
    val waterTarget: Flow<Int> = context.dataStore.data.map { it[KEY_TARGET_WATER] ?: 8 }
    val relaxationTarget: Flow<Int> = context.dataStore.data.map { it[KEY_TARGET_RELAXATION] ?: 15 }
    val sleepTarget: Flow<Int> = context.dataStore.data.map { it[KEY_TARGET_SLEEP] ?: 8 }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_ONBOARDING_COMPLETED] = completed
        }
    }

    suspend fun setUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USER_NAME] = name
        }
    }

    suspend fun setReminderTime(time: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_REMINDER_TIME] = time
        }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_NOTIFICATIONS_ENABLED] = enabled
        }
    }

    suspend fun setTarget(goalType: String, value: Int) {
        context.dataStore.edit { preferences ->
            when (goalType) {
                "meals" -> preferences[KEY_TARGET_MEALS] = value
                "movement" -> preferences[KEY_TARGET_MOVEMENT] = value
                "water" -> preferences[KEY_TARGET_WATER] = value
                "relaxation" -> preferences[KEY_TARGET_RELAXATION] = value
                "sleep" -> preferences[KEY_TARGET_SLEEP] = value
            }
        }
    }

    suspend fun resetAll() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
