package com.example.mit
import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(onLogout: () -> Unit, onNavigate: (String) -> Unit) {
    var permmissionStatus by remember { mutableStateOf("Permission not request") }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {garanted ->


        permmissionStatus= if(garanted) "Permission garanted" else "Permission not garanted"

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Bienvenido a la aplicación", )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onNavigate("MisTarjetas") }) {
            Text("Mis Tarjetas")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            permissionLauncher.launch(  Manifest.permission.ACCESS_COARSE_LOCATION)
            permissionLauncher.launch(  Manifest.permission.ACCESS_FINE_LOCATION)
            onNavigate("Pagar") }) {
            Text("Pagar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { onNavigate("MisMovimientos") }) {
            Text("MisMovimientos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onLogout, colors = ButtonDefaults.buttonColors(Color.Red) ) {
            Text("Cerrar Sesión", color = Color.White)
        }

}

}