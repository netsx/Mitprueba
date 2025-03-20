package com.example.mit.tarjetas


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mit.tables.TarjetaEntity
import com.example.mit.viewmodel.TarjetaViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NuevaTarjeta(navController: NavController, viewModel: TarjetaViewModel) {
    var nombreTarjetahabiente by remember { mutableStateOf("") }
    var numeroTarjeta by remember { mutableStateOf("") }
    var fechaExpiracion by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
           // TopAppBar(title = { Text("Agregar Tarjeta") })
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = nombreTarjetahabiente,
                onValueChange = { nombreTarjetahabiente = it },
                label = { Text("Nombre del Tarjetahabiente") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = numeroTarjeta,
                onValueChange = { numeroTarjeta = it },
                label = { Text("Número de Tarjeta") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fechaExpiracion,
                onValueChange = { fechaExpiracion = it },
                label = { Text("Fecha de Expiración") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {

                    viewModel.agregarTarjeta(nombreTarjetahabiente = nombreTarjetahabiente,
                        numeroTarjeta = numeroTarjeta,
                        fechaExpiracion = fechaExpiracion)


                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Tarjeta")
            }
        }
    }

}