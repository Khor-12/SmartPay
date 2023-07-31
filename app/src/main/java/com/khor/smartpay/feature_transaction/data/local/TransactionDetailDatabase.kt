package com.khor.smartpay.feature_transaction.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khor.smartpay.feature_transaction.data.local.entity.TransactionDetailEntity

@Database(
    entities = [TransactionDetailEntity::class],
    version = 1
)
abstract class TransactionDetailDatabase: RoomDatabase() {

    abstract val dao: TransactionDetailDao
}