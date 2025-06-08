package com.okdm.foxus

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATA_STORE_NAME = "timer_data"
private val Context.dataStore by preferencesDataStore(name = DATA_STORE_NAME)

object TimerDataStore {
    private val TIMER_CARDS_KEY = stringPreferencesKey("timer_cards")
    private val NEXT_ID_KEY = intPreferencesKey("next_id")
    private val gson = Gson()

    suspend fun saveTimerCards(context: Context, timerCards: List<TimerCard>) {
        val json = gson.toJson(timerCards)
        context.dataStore.edit { preferences ->
            preferences[TIMER_CARDS_KEY] = json
        }
    }

    suspend fun saveNextId(context: Context, nextId: Int) {
        context.dataStore.edit { preferences ->
            preferences[NEXT_ID_KEY] = nextId
        }
    }

    fun getTimerCardsFlow(context: Context): Flow<List<TimerCard>> {
        return context.dataStore.data.map { preferences ->
            val json = preferences[TIMER_CARDS_KEY]
            if (json != null) {
                val type = object : TypeToken<List<TimerCard>>() {}.type
                gson.fromJson<List<TimerCard>>(json, type)
            } else {
                emptyList()
            }
        }
    }

    fun getNextIdFlow(context: Context): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[NEXT_ID_KEY] ?: 1
        }
    }
}