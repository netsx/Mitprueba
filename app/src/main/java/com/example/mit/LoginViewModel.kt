package com.example.mit
import UserPreferences
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val userPreferences = UserPreferences(application.applicationContext)

    suspend fun login(username: String, password: String): Boolean {
        // Simulación de autenticación
        return if (username.length >= 6 && password.length >= 6) {
            userPreferences.setLoggedIn(true)
            true
        } else {
            false
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.setLoggedIn(false)
        }
    }

    suspend fun isUserLoggedIn(): Boolean {
        return userPreferences.isLoggedIn.first()
    }
}
