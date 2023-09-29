package com.khor.smartpay.feature_auth.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.khor.smartpay.feature_auth.data.local.entity.UserPreferenceEntity

@Dao
interface UserPreferencesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPreference(pref: UserPreferenceEntity)

    @Query("SELECT userType FROM UserPreferenceEntity")
    fun getUserType(): String?

    @Query("SELECT isUserVerified FROM UserPreferenceEntity")
    fun isUserVerified(): Boolean?
}
