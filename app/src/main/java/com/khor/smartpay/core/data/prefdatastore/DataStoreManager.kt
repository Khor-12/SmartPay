package com.khor.smartpay.core.data.prefdatastore

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userToken")
        private val USER_VERIFIED = booleanPreferencesKey("user_verified")
        private val COLOR_SCHEME = intPreferencesKey("color_scheme")
    }

    val getAccessToken: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[USER_VERIFIED] ?: false
    }

    val getColorScheme: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[COLOR_SCHEME] ?: 0
    }

    suspend fun saveToken(token: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[USER_VERIFIED] = token
        }
    }

    suspend fun saveColorScheme(value: Int) {
        context.dataStore.edit { preferences ->
            preferences[COLOR_SCHEME] = value
        }
    }

    suspend fun clearDataStore() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
