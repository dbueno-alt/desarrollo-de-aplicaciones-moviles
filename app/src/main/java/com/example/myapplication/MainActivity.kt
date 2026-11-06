package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MasterMartiniTheme
import com.example.myapplication.viewmodel.AuthViewModel
import com.example.myapplication.views.HomeScreen
import com.example.myapplication.views.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MasterMartiniTheme {
                val navController = rememberNavController()
                val viewModel = remember { AuthViewModel() }

                NavHost(navController = navController, startDestination = "login") {

                    composable("register") {
                        com.example.myapplication.views.RegisterScreen(navController)
                    }

                    composable("login") {
                        LoginScreen(navController, viewModel)
                    }

                    composable("home/{email}") { backStack ->
                        val email = backStack.arguments?.getString("email")
                        HomeScreen()
                    }

                }
            }
        }
    }
}