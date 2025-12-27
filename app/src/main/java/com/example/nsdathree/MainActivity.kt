package com.example.nsdathree

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nsdathree.screens.MapScreen
import com.example.nsdathree.screens.ProfileScreen
import com.example.nsdathree.ui.theme.NSDAThreeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NSDAThreeTheme {
                NavHost(
                    navController = navController,
                    startDestination = "auth"
                ){
                    composable("auth") {
                        AuthScreen(navController = navController)
                    }
                    composable("main") {
                        MainScreen(navController = navController)
                    }
                    composable("map") {
                        MapScreen()
                    }
                    composable(
                        route = "profile/{userId}",
                        arguments = listOf(navArgument("userId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId") ?: ""
                        ProfileScreen(userId = userId)
                    }
                }
            }
        }
    }
}