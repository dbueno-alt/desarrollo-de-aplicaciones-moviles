package com.example.myapplication.views

import android.Manifest
import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.model.Usuario
import com.example.myapplication.model.UsuarioRepository
import com.example.myapplication.ui.theme.VerdeMartini
import com.example.myapplication.utils.Validaciones
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var fechaNac by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var rolSeleccionado by remember { mutableStateOf("Seleccionar Rol") }
    var expandirMenuRol by remember { mutableStateOf(false) }
    val opcionesRol = listOf("Chef o repostero", "Casual o dueño de hogar", "Encargado de negocio o dueño")

    var checkPublicidad by remember { mutableStateOf(false) }
    var checkTerminos by remember { mutableStateOf(false) }
    var mostrarTerminos by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var fotoPerfil by remember { mutableStateOf<Bitmap?>(null) }

    val lanzadorCamara = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        fotoPerfil = bitmap
    }

    val lanzadorPermisos = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permisos ->
        val camaraConcedida = permisos[Manifest.permission.CAMERA] ?: false
        if (camaraConcedida) {
            lanzadorCamara.launch(null)
        } else {
            mensajeError = "Se requiere permiso de cámara para la foto de perfil"
        }
    }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            fechaNac = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.logo_master_martini),
                contentDescription = "Logo Master Martini",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(bottom = 8.dp)
            )

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                if (fotoPerfil != null) {
                    Image(
                        bitmap = fotoPerfil!!.asImageBitmap(),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    IconButton(onClick = {
                        lanzadorPermisos.launch(
                            arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)
                        )
                    }) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Tomar Foto", modifier = Modifier.size(40.dp))
                    }
                }
            }
            Text("Añadir foto de perfil", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 8.dp))
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre y Apellido") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = rut, onValueChange = { rut = it }, label = { Text("RUT (ej. 12345678-9)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), modifier = Modifier.fillMaxWidth())

            OutlinedTextField(
                value = fechaNac,
                onValueChange = { fechaNac = it },
                label = { Text("Fecha de Nacimiento (DD/MM/AAAA)") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = { TextButton(onClick = { datePickerDialog.show() }) { Text("📅") } }
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Text("Mín 12 carac., 1 mayúscula, 1 número y 1 símbolo", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(10.dp))

            ExposedDropdownMenuBox(expanded = expandirMenuRol, onExpandedChange = { expandirMenuRol = !expandirMenuRol }) {
                OutlinedTextField(
                    value = rolSeleccionado, onValueChange = {}, readOnly = true,
                    label = { Text("Tag de Usuario") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirMenuRol) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expandirMenuRol, onDismissRequest = { expandirMenuRol = false }) {
                    opcionesRol.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = { rolSeleccionado = opcion; expandirMenuRol = false }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = checkPublicidad, onCheckedChange = { checkPublicidad = it })
                Text("Deseo recibir correos publicitarios")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = checkTerminos, onCheckedChange = { checkTerminos = it })
                TextButton(onClick = { mostrarTerminos = true }) {
                    Text("Aceptar Términos y Condiciones", color = VerdeMartini)
                }
            }

            if (mensajeError.isNotEmpty()) {
                Text(mensajeError, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(8.dp))
            }

            Button(
                onClick = {
                    when {
                        nombre.isBlank() -> mensajeError = "Ingrese nombre"
                        !Validaciones.isRutValid(rut) -> mensajeError = "RUT inválido"
                        !Validaciones.isEmailValid(email) -> mensajeError = "Formato de correo inválido"
                        !Validaciones.isMayorDeEdad(fechaNac) -> mensajeError = "Debe ser mayor de 18 años"
                        !Validaciones.isPasswordValid(password) -> mensajeError = "Contraseña débil"
                        rolSeleccionado == "Seleccionar Rol" -> mensajeError = "Seleccione un rol"
                        !checkTerminos -> mensajeError = "Debe aceptar los términos"
                        else -> {
                            val repositorio = UsuarioRepository(context)

                            val nuevoUsuario = Usuario(
                                nombre = nombre,
                                rut = rut,
                                email = email.trim(),
                                fechaNacimiento = fechaNac,
                                password = password.trim(),
                                rol = rolSeleccionado,
                                recibePublicidad = checkPublicidad
                            )

                            if (repositorio.registrar(nuevoUsuario)) {
                                mensajeError = ""
                                navController.navigate("login")
                            } else {
                                mensajeError = "Este correo ya se encuentra registrado"
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = VerdeMartini),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Registrarse")
            }

            TextButton(
                onClick = { navController.navigate("login") },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("¿Ya tienes cuenta? Inicia sesión", color = VerdeMartini)
            }
        }
    }

    if (mostrarTerminos) {
        AlertDialog(
            onDismissRequest = { mostrarTerminos = false },
            title = { Text("Términos y condiciones de uso", color = VerdeMartini) },
            text = {
                Text(
                    "1. El usuario promete no compartir el material prestado por la aplicación a terceros con un interés económico.\n" +
                            "2. El usuario promete usar el material y recetas siguiendo el procedimiento otorgado y libra de cualquier responsabilidad legal a la empresa en caso de accidente.\n" +
                            "3. La aplicación se reserva el derecho de agregar y modificar las condiciones de uso con el aviso apropiado y el consentimiento expreso de los usuarios.\n\n" +
                            "Agradecemos su aprobación y le deseamos la mejor experiencia culinaria en nuestra aplicación."
                )
            },
            confirmButton = {
                TextButton(onClick = { checkTerminos = true; mostrarTerminos = false }) {
                    Text("Aceptar", color = VerdeMartini)
                }
            }
        )
    }
}