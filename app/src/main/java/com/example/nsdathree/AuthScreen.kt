package com.example.nsdathree

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nsdathree.repository.LocationRepository
import com.example.nsdathree.viewmodel.AuthViewModel


@Composable
fun AuthScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
){
    var clicked by remember { mutableStateOf(false) }
    val top = if (clicked) "Login"  else "Welcome"
    val bottom = if (clicked) "Don't have any account!" else "Already have any account!"
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            top,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Column() {
            when(clicked){
                true -> LoginPart(navController = navController)
                false -> RegPart(navController = navController)
            }
        }
        Text(
            bottom,
            modifier = Modifier
                .clickable(
                    onClick = {
                        clicked = !clicked
                    }
                )
        )
    }
}

@Composable
fun LoginPart(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
){
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by remember { mutableStateOf("") }
        var pass by remember { mutableStateOf("") }

        Spacer(modifier = Modifier.height(22.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            shape = RoundedCornerShape(12.dp),
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = pass,
            onValueChange = {pass = it},
            shape = RoundedCornerShape(12.dp),
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                authViewModel.login(
                    email = email,
                    password = pass,
                    onSuccess = {
                        navController.navigate("main") {
                            popUpTo("auth") { inclusive = true }
                        }
                    },
                    onError = {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        ) {
            Text("Login")
        }


        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun RegPart(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
){
    val context = LocalContext.current
    val locationRepo = remember { LocationRepository(context) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            Toast.makeText(context, "Location permission required", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by remember { mutableStateOf("") }
        var pass by remember { mutableStateOf("") }
        var con by remember { mutableStateOf("") }

        Spacer(modifier = Modifier.height(22.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            shape = RoundedCornerShape(12.dp),
            label = { Text("Email") },
            modifier = Modifier
                    .fillMaxWidth()
                .padding(horizontal = 18.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = pass,
            onValueChange = {pass = it},
            shape = RoundedCornerShape(12.dp),
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = con,
            onValueChange = {con = it},
            shape = RoundedCornerShape(12.dp),
            label = { Text("Re-enter Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (pass != con) {
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

                locationRepo.getLastLocation(
                    onSuccess = { latLng ->
                        authViewModel.register(
                            email = email,
                            password = pass,
                            lat = latLng.latitude,
                            lng = latLng.longitude,
                            onSuccess = {
                                navController.navigate("main") {
                                    popUpTo("auth") { inclusive = true }
                                }
                            },
                            onError = {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    onError = {
                        Toast.makeText(context, "Enable location permission", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        ) {
            Text("Register")
        }


        Spacer(modifier = Modifier.height(12.dp))
    }
}