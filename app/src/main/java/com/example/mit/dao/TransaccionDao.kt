package com.example.mit.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mit.tables.TransaccionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransaccionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaccion(transaccion: TransaccionEntity)

    @Query("SELECT * FROM transacciones ORDER BY id DESC")
    fun getAllTransacciones(): Flow<List<TransaccionEntity>>
}
