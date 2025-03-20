package com.example.mit.pagar

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mit.device.obtenerUbicacion
import com.example.mit.viewmodel.TarjetaViewModel
import com.example.mit.viewmodel.TransaccionViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PagarScreen(viewModel: TarjetaViewModel, navController: NavController) {
    var permmissionStatus by remember { mutableStateOf("Permission not request") }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {garanted ->


        permmissionStatus= if(garanted) "Permission garanted" else "Permission not garanted"

    }



    val  viewModelTransacion: TransaccionViewModel = viewModel();
    var ubicacion by remember { mutableStateOf<Pair<Double, Double>?>(null) }
    val  tarjetas = viewModel.allTarjetas.collectAsState(initial = emptyList())
    var tarjetaSeleccionada by remember { mutableStateOf("") }
    var tarjetaDestinatario by remember { mutableStateOf("") }
    var nombreDestinatario by remember { mutableStateOf("") }
    var locationGPS by remember { mutableStateOf("") }
    var motivoPago by remember { mutableStateOf("") }

    val context = LocalContext.current
    //val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    var latitude by remember { mutableStateOf<String?>(null) }
    var longitude by remember { mutableStateOf<String?>(null) }

    Scaffold {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Selecciona tu tarjeta")

            var expanded by remember { mutableStateOf(false) }
            Box {
                Button(onClick = { expanded = true }) {
                    Text(text = if (tarjetaSeleccionada.isEmpty()) "Elige una tarjeta" else tarjetaSeleccionada)
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    tarjetas.value.forEach { tarjeta ->
                        DropdownMenuItem(
                            text = { Text(tarjeta.numeroTarjeta) },
                            onClick = {
                                tarjetaSeleccionada = tarjeta.numeroTarjeta
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = tarjetaDestinatario,
                onValueChange = { tarjetaDestinatario = it },
                label = { Text("Tarjeta destinatario") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = nombreDestinatario,
                onValueChange = { nombreDestinatario = it },
                label = { Text("Nombre destinatario") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = motivoPago,
                onValueChange = { motivoPago = it },
                label = { Text("Motivo de pago") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {

                    if (tarjetaSeleccionada.isNotEmpty() && tarjetaDestinatario.isNotEmpty()) {
                        val dateTime = getDateTimeFormatted(50, "dd/MM/yyyy HH:mm:ss")


                        if (ActivityCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED ||
                            ActivityCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {


                            var local :FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
                            local.lastLocation.addOnSuccessListener { location ->
                                if (location != null) {
                                    ubicacion = Pair(location.latitude, location.longitude)
                                    viewModelTransacion.agregarTransaccion(tarjetaSeleccionada, tarjetaDestinatario,
                                        nombreDestinatario,motivoPago,"$dateTime","$ubicacion.latitude,$ubicacion.longitude" )
                                    navController.popBackStack()

                                }else{
                                    Toast.makeText(context, "No se pudo obtener la ubicación revisa tus permisos y vuelve a intentarlo", Toast.LENGTH_SHORT).show()
                                }
                            }

                        } else {

                            Toast.makeText(context, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
                        }

                        Toast.makeText(context, "Completa los campos soliciatados", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Realizar Pago")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDateTimeFormatted(minutesToAdd: Long, pattern: String): String {

    val nowInUtc = OffsetDateTime.now(ZoneOffset.UTC)
    val someMinutesLater = nowInUtc.plusMinutes(minutesToAdd)

    return someMinutesLater.format(
        DateTimeFormatter.ofPattern(pattern)
    )
    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    @SuppressLint("MissingPermission")
    fun getLocation(context: Context, callback: (Double, Double) -> Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                callback(location.latitude, location.longitude)
            }
        }
    }
}