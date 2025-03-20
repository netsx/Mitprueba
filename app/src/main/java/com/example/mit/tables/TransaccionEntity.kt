package com.example.mit.tables
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transacciones")
data class TransaccionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tarjetaConfigurada: String,
    val tarjetaDestinatario: String,
    val nombreDestinatario: String,
    val motivoPago: String,
    val fecha: String,
    val location: String
)