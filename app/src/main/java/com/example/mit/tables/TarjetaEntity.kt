package com.example.mit.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tarjetas")
data class TarjetaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombreTarjetahabiente: String,
    val numeroTarjeta: String,
    val fechaExpiracion: String
)