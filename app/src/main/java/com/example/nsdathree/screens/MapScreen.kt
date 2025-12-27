package com.example.nsdathree.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nsdathree.viewmodel.UserViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(
    userViewModel: UserViewModel = viewModel()
) {
    // Load users from Firestore
    LaunchedEffect(Unit) {
        userViewModel.loadAllUsers()
    }

    val users = userViewModel.users

    val cameraPositionState = rememberCameraPositionState()

    // Move camera once users are loaded
    LaunchedEffect(users) {
        if (users.isNotEmpty()) {
            val target = if (users.size == 1) {
                LatLng(users.first().latitude, users.first().longitude)
            } else {
                // Calculate center of all points
                val lat = users.map { it.latitude }.average()
                val lng = users.map { it.longitude }.average()
                LatLng(lat, lng)
            }
            cameraPositionState.position = CameraPosition.fromLatLngZoom(target, 14f)
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {
        users.forEach { user ->
            Marker(
                state = MarkerState(
                    position = LatLng(user.latitude, user.longitude)
                ),
                title = user.displayName ?: "Unnamed",
                snippet = user.email
            )
        }
    }
}