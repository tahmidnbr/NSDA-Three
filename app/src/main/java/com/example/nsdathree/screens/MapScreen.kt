package com.example.nsdathree.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nsdathree.repository.AuthRepository
import com.example.nsdathree.viewmodel.UserViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay

@Composable
fun MapScreen(
    userViewModel: UserViewModel = viewModel(),
    authRepository: AuthRepository = AuthRepository()
) {
    // Load all users
    LaunchedEffect(Unit) {
        userViewModel.loadAllUsers()
    }

    val users = userViewModel.users

    // Filter valid locations
    val validUsers = users.filter {
        it.latitude != 0.0 &&
                it.longitude != 0.0 &&
                it.latitude in -90.0..90.0 &&
                it.longitude in -180.0..180.0
    }

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(validUsers) {
        if (validUsers.isEmpty()) return@LaunchedEffect

        val currentUserId = authRepository.currentUser()?.uid
        val currentUser = validUsers.find { it.userId == currentUserId }

        // STEP 1: Focus on logged-in user
        currentUser?.let {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(it.latitude, it.longitude),
                    16f
                ),
                800
            )
        }

        // STEP 2: Expand to show all users
        delay(1000)

        if (validUsers.size > 1) {
            val boundsBuilder = LatLngBounds.builder()
            validUsers.forEach {
                boundsBuilder.include(
                    LatLng(it.latitude, it.longitude)
                )
            }

            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngBounds(
                    boundsBuilder.build(), 120),
                1000
            )
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        validUsers.forEach { user ->
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
