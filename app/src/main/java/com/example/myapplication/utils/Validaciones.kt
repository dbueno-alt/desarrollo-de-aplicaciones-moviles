package com.example.myapplication.utils


import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
object Validaciones {

    // esta funcion verifica formato de rut usando el algoritmo
    // interactua con funciones de String para limpiar el texto y realizar la logica matematica. // DB
    fun isRutValid(rut: String): Boolean {
        val limpio = rut.replace(".", "").replace("-", "").uppercase()
        if (limpio.length < 2) return false

        val cuerpo = limpio.dropLast(1)
        val dv = limpio.last()
        var suma = 0
        var multiplicador = 2

        // Invertir los numeros y multiplicar por la serie // DB
        for (i in cuerpo.reversed()) {
            if (!i.isDigit()) return false
            suma += i.digitToInt() * multiplicador
            multiplicador = if (multiplicador == 7) 2 else multiplicador + 1
        }

        val resto = suma % 11
        val dvCalculado = (11 - resto).toString().let {
            when (it) {
                "11" -> "0"
                "10" -> "K"
                else -> it
            }
        }
        return dv.toString() == dvCalculado
    }

    // esta funcion verifica que el correo cumpla la estructura basica de un email... // DB
    fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
        return emailRegex.matches(email)
    }

    // esta funcion verifica que la contraseña tenga al menos 12 caracteres, mayuscula, numero y caracter especial... // DB
    fun isPasswordValid(password: String): Boolean {
        val passRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!]).{12,}\$".toRegex()
        return passRegex.matches(password)
    }

    // esta funcion verifica que la fecha ingresada sea valida y que el usuario tenga mas de 18 años... // DB
    fun isMayorDeEdad(fechaNacimiento: String): Boolean {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.isLenient = false
            val fechaNac = sdf.parse(fechaNacimiento) ?: return false

            val hoy = Calendar.getInstance()
            val nac = Calendar.getInstance()
            nac.time = fechaNac

            var edad = hoy.get(Calendar.YEAR) - nac.get(Calendar.YEAR)

            if (hoy.get(Calendar.DAY_OF_YEAR) < nac.get(Calendar.DAY_OF_YEAR)) {
                edad--
            }

            edad >= 18
        } catch (e: Exception) {
            false
        }
    }
}