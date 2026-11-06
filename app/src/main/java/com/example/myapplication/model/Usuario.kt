package com.example.myapplication.model

data class Usuario(
    val nombre: String,
    val rut: String,
    val email: String,
    val fechaNacimiento: String,
    val password: String,
    val rol: String,
    val recibePublicidad: Boolean
)