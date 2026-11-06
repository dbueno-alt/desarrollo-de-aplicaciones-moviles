package com.example.myapplication.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.viewmodel.AuthViewModel
import com.example.myapplication.ui.theme.DoradoMartini
import com.example.myapplication.ui.theme.NegroMartini
import com.example.myapplication.ui.theme.RojoMartini
import com.example.myapplication.ui.theme.VerdeMartini

@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_master_martini),
            contentDescription = "Logo Master Martini",
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
            isError = viewModel.errorVisual.value,
            singleLine = true
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
            isError = viewModel.errorVisual.value,
            singleLine = true
        )

        AnimatedVisibility(visible = viewModel.mensaje.value.isNotEmpty()) {
            Text(viewModel.mensaje.value, color = RojoMartini, modifier = Modifier.padding(top = 10.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (viewModel.login(context, email, password)) {
                    navController.navigate("home/$email")
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = DoradoMartini)
        ) {
            Text("Entrar", color = NegroMartini)
        }
        TextButton(onClick = { navController.navigate("register") }) {
            Text("¿No tienes cuenta? Regístrate", color = VerdeMartini)
        }
    }
}