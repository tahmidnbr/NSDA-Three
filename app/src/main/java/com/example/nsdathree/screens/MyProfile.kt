package com.example.nsdathree.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nsdathree.viewmodel.UserViewModel
import com.google.android.gms.location.LocationServices

@Composable
fun ProfileScreen(
    userId: String, // Pass the current user's ID
    userViewModel: UserViewModel = viewModel(),
    context: Context = LocalContext.current
) {
    var displayName by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var locationText by remember { mutableStateOf("Unknown") }

    val scope = rememberCoroutineScope()
    val fusedClient = LocationServices.getFusedLocationProviderClient(context)

    // Load user info initially
    LaunchedEffect(userId) {
        val user = userViewModel.users.find { it.userId == userId }
        user?.let { displayName = it.displayName ?: "" }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(text = "Edit Profile")

        OutlinedTextField(
            value = displayName,
            onValueChange = { displayName = it },
            label = { Text("Display Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                isLoading = true
                userViewModel.updateDisplayName(userId, displayName)
                isLoading = false
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Display Name")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Current Location: $locationText")

        Button(
            onClick = {
                isLoading = true
                // Get last location
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(context, "Location permission required", Toast.LENGTH_SHORT).show()
                    isLoading = false
                    return@Button
                }

                fusedClient.lastLocation
                    .addOnSuccessListener { location ->
                        if (location != null) {
                            userViewModel.updateLocation(
                                userId,
                                location.latitude,
                                location.longitude
                            )
                            locationText = "${location.latitude}, ${location.longitude}"
                        } else {
                            Toast.makeText(context, "Location not available", Toast.LENGTH_SHORT).show()
                        }
                        isLoading = false
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Failed to get location", Toast.LENGTH_SHORT).show()
                        isLoading = false
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update Location")
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}
