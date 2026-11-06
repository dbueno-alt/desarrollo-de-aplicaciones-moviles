package com.example.myapplication.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.UsuarioRepository

class AuthViewModel : ViewModel() {
    var mensaje = mutableStateOf("")
    var errorVisual = mutableStateOf(false)
    var usuarioActual = mutableStateOf<String?>(null)


    fun login(context: Context, email: String, password: String): Boolean {
        val emailLimpio = email.trim()
        val passLimpio = password.trim()
        val repo = UsuarioRepository(context)

        return if (repo.login(emailLimpio, passLimpio)) {
            usuarioActual.value = emailLimpio
            mensaje.value = "Inicio de sesión exitoso"
            errorVisual.value = false
            true
        } else {
            mensaje.value = "Credenciales inválidas"
            errorVisual.value = true
            false
        }
    }
}