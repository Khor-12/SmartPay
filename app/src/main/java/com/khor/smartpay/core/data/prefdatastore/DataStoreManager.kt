package com.khor.smartpay.core.data.prefdatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userToken")
        private val USER_VERIFIED = booleanPreferencesKey("user_verified")
    }

    val getAccessToken: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[USER_VERIFIED] ?: false
    }

    suspend fun saveToken(token: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[USER_VERIFIED] = token
        }
    }
}