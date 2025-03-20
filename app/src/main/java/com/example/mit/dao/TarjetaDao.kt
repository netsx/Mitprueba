package com.example.mit.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mit.tables.TarjetaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TarjetaDao {
    @Insert
    suspend fun insertTarjeta(tarjeta: TarjetaEntity)

    @Query("SELECT * FROM tarjetas")
    fun getAllTarjetas(): Flow<List<TarjetaEntity>>
}