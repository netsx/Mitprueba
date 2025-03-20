package com.example.mit

import LoginScreen
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mit.movimientos.MisMovimientos
import com.example.mit.pagar.PagarScreen
import com.example.mit.tarjetas.MisTarjetas
import com.example.mit.tarjetas.NuevaTarjeta
import com.example.mit.viewmodel.TarjetaViewModel
import com.example.mit.viewmodel.TransaccionViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: LoginViewModel = viewModel()
            val coroutineScope = rememberCoroutineScope()
            val viewModelTarjetaViewModel: TarjetaViewModel = viewModel()
            val viewModelTransaccionViewModel: TransaccionViewModel = viewModel()
            var isLoggedIn by remember { mutableStateOf(false) }


            LaunchedEffect(Unit) {
                isLoggedIn = viewModel.isUserLoggedIn()
            }

            val navController = rememberNavController()

            if (isLoggedIn) {
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(


                            onLogout = {
                                coroutineScope.launch {
                                    isLoggedIn = false
                                    navController.popBackStack("login", inclusive = true)
                                }
                            },
                            onNavigate = { destination ->
                                navController.navigate(destination)
                            }
                        )
                    }
                    composable("MisTarjetas") {
                        MisTarjetas(onNavigate = { destination ->
                            navController.navigate(destination)
                        })
                    }
                    composable("NuevaTarjeta") {
                        val viewModelTarjetaViewModel: TarjetaViewModel = viewModel()
                        NuevaTarjeta(
                            navController = navController,
                            viewModel = viewModelTarjetaViewModel
                        )
                    }
                    composable("MisTarjetas") {
                        MisTarjetas(onNavigate = { destination ->
                            navController.navigate(destination)
                        })
                    }


                    composable("Pagar") {
                        PagarScreen(viewModel = viewModelTarjetaViewModel, navController = navController)

                    }
                    composable("MisMovimientos") {
                        MisMovimientos (viewModel = viewModelTransaccionViewModel, onNavigate = { destination ->
                            navController.navigate(destination)
                        })

                    }
                }
            } else {
                LoginScreen {
                    isLoggedIn = true
                }
            }
        }
    }
}