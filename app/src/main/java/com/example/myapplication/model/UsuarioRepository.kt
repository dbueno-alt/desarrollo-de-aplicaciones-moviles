package com.example.myapplication.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UsuarioRepository(context: Context) {
    private val prefs = context.getSharedPreferences("MasterMartiniPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    private fun obtenerUsuarios(): MutableList<Usuario> {
        val json = prefs.getString("usuarios", null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<Usuario>>() {}.type
            gson.fromJson(json, type)
        } else {
            mutableListOf()
        }
    }

    fun registrar(usuario: Usuario): Boolean {
        val usuarios = obtenerUsuarios()
        if (usuarios.any { it.email == usuario.email }) return false
        usuarios.add(usuario)
        // Convertimos la lista a texto JSON y la guardamos //DB
        prefs.edit().putString("usuarios", gson.toJson(usuarios)).apply()
        return true
    }

    fun login(email: String, password: String): Boolean {
        val usuarios = obtenerUsuarios()
        return usuarios.any { it.email == email && it.password == password }
    }
}