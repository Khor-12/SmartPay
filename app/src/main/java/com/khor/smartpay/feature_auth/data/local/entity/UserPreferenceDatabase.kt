package com.khor.smartpay.feature_auth.data.local.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khor.smartpay.feature_auth.data.local.UserPreferencesDao
import com.khor.smartpay.feature_transaction.data.local.TransactionDetailDao

@Database(
    entities = [UserPreferenceEntity::class],
    version = 1
)
abstract class UserPreferenceDatabase : RoomDatabase() {

    abstract val dao: UserPreferencesDao
}