package com.okdm.foxus

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class DataStoreManager(val context: Context) {
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore("FocusTimerData")
    private fun nameKeyForTimer(id: String) = stringPreferencesKey("name_$id")
    private fun focusTimeKeyForTimer(id: String) = intPreferencesKey("focusTime_$id")
    private fun restTimeKeyForTimer(id: String) = intPreferencesKey("restTime_$id")

    suspend fun saveFocusTimerData(timer: FOCUS_TIMER){
        context.datastore.edit { preferences ->
            preferences[nameKeyForTimer(timer.id)] = timer.name
            preferences[focusTimeKeyForTimer(timer.id)] = timer.focusTime
            preferences[restTimeKeyForTimer(timer.id)] = timer.restTime
        }
    }

    suspend fun loadFocusTimer(id: String): FOCUS_TIMER? {
        val preferences = context.datastore.data.first()
        val name = preferences[nameKeyForTimer(id)] ?: return null
        val focusTime = preferences[focusTimeKeyForTimer(id)] ?: 25
        val restTime = preferences[restTimeKeyForTimer(id)] ?: 5

        return FOCUS_TIMER(id, name, focusTime, restTime)
    }
    
}