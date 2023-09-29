package com.khor.smartpay.core.data.prefdatastore

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.khor.smartpay.core.util.AppSettings
import com.khor.smartpay.core.util.AppSettingsSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore(private val context: Context) {

    companion object {
        private val Context.dataStore by dataStore("app-settings.json", AppSettingsSerializer)
    }

    val getUserType: Flow<String> = context.dataStore.data.map {
        it.userType
    }

    val getAccessToken: Flow<Boolean> = context.dataStore.data.map {
        it.isUserVerified
    }

    suspend fun saveUserTypeAndToken(userType: String, token: Boolean) {
        context.dataStore.updateData {
            it.copy(
                userType = userType,
                isUserVerified = token
            )
        }
    }

    suspend fun clearDataStore() {
        context.dataStore.updateData {
            AppSettings()
        }
    }
}
