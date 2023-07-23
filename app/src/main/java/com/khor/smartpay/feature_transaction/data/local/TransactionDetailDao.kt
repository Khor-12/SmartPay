package com.khor.smartpay.feature_transaction.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khor.smartpay.feature_transaction.data.local.entity.TransactionDetailEntity

@Dao
interface TransactionDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactionsDetails(transactions: List<TransactionDetailEntity>)

    @Query("SELECT * FROM transactiondetailentity")
    suspend fun getTransactions(): List<TransactionDetailEntity>

    @Query("DELETE FROM transactiondetailentity")
    suspend fun deleteTransactions()
}
