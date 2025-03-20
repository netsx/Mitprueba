package com.example.mit.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mit.dao.TarjetaDao
import com.example.mit.database.AppDatabase
import com.example.mit.tables.TarjetaEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TarjetaViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val tarjetaDao = db.tarjetaDao()

    val allTarjetas: Flow<List<TarjetaEntity>> = tarjetaDao.getAllTarjetas()


    fun agregarTarjeta(nombreTarjetahabiente: String, numeroTarjeta: String, fechaExpiracion: String) {
        viewModelScope.launch {
            val tarjeta = TarjetaEntity(nombreTarjetahabiente = nombreTarjetahabiente, numeroTarjeta = numeroTarjeta, fechaExpiracion = fechaExpiracion)
            tarjetaDao.insertTarjeta(tarjeta)
            Log.e("TarjetaViewModel", "Tarjeta agregada: $tarjeta")
        }
    }
}