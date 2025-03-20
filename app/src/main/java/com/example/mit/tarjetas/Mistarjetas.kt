package com.example.mit.tarjetas

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mit.tables.TarjetaEntity
import com.example.mit.viewmodel.TarjetaViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MisTarjetas(viewModelTarjetaViewModel: TarjetaViewModel = viewModel(),onNavigate: (String) -> Unit) {

   val  tarjetas = viewModelTarjetaViewModel.allTarjetas.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {

            //TopAppBar(title = { Text("Gestión de Tarjetas") })
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {



            Button(
                onClick = {

                    onNavigate("NuevaTarjeta")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar Nueva Tarjeta")
            }


            Text("Tarjetas Guardadas", style = MaterialTheme.typography.headlineSmall)

            LazyColumn {

                items(
                    tarjetas.value.size,
                    key = { tarjeta -> tarjetas.value[tarjeta].id }
                ) { tarjeta ->

                    TarjetaItem(tarjetas.value[tarjeta])
                }
            }
        }
    }
}
    @Composable
    fun TarjetaItem(tarjeta: TarjetaEntity) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Nombre: ${tarjeta.nombreTarjetahabiente}")
                Text(text = "Número: ${tarjeta.numeroTarjeta}")
                Text(text = "Expira: ${tarjeta.fechaExpiracion}")
            }
        }
    }