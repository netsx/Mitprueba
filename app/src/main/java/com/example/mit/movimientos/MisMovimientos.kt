package com.example.mit.movimientos

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mit.tables.TransaccionEntity
import com.example.mit.viewmodel.TransaccionViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun MisMovimientos(viewModel: TransaccionViewModel ,onNavigate: (String) -> Unit) {


        val  movimientos = viewModel.allMovimientos.collectAsState(initial = emptyList())

        Scaffold(
            topBar = {

                //TopAppBar(title = { Text("Gestión de Tarjetas") })
            }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {



                Text("Mis Movimientos", style = MaterialTheme.typography.headlineSmall)

                LazyColumn {

                    items(
                        movimientos.value.size,
                        key = { tarjeta -> movimientos.value[tarjeta].id }
                    ) { tarjeta ->

                        Movimiento(movimientos.value[tarjeta])
                    }
                }
            }
        }
    }
    @Composable
    fun Movimiento(movimientos: TransaccionEntity) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Tarjeta: ${movimientos.tarjetaConfigurada}")
                Text(text = "Destinatario: ${movimientos.nombreDestinatario}")
                Text(text = "Hora: ${movimientos.fecha}")
                Text(text = "Localizacion: ${movimientos.location}")
            }
        }
    }
