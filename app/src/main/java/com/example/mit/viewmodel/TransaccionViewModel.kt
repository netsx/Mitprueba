package com.example.mit.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mit.database.AppDatabase
import com.example.mit.tables.TarjetaEntity
import com.example.mit.tables.TransaccionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.String


class TransaccionViewModel(application: Application) : AndroidViewModel(application) {
        private val db = AppDatabase.getDatabase(application)
        private val tarjetaDaotransaccionDao = db.transaccionDao()

        val allMovimientos: Flow<List<TransaccionEntity>> = tarjetaDaotransaccionDao.getAllTransacciones()


        fun agregarTransaccion(tarjetaConfigurada : String,
                               tarjetaDestinatario : String,
                               nombreDestinatario : String,
                               motivoPago : String,
                               fecha : String,
                               location : String) {
            viewModelScope.launch {
                val tarjeta = TransaccionEntity(tarjetaConfigurada = tarjetaConfigurada,
                 tarjetaDestinatario = tarjetaDestinatario,
                 nombreDestinatario= nombreDestinatario,
                 motivoPago = motivoPago,
                 fecha = fecha,
                 location = location)

                tarjetaDaotransaccionDao.insertTransaccion(tarjeta)
                Log.e("TarjetaViewModel", "Tarjeta agregada: $tarjeta")
            }
        }
    }