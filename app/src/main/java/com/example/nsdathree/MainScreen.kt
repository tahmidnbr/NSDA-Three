package com.example.nsdathree

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nsdathree.screens.FriendListScreen


@Composable
fun MainScreen(
    navController: NavController
){
    Scaffold(
        floatingActionButton = {
            FabMenu(navController = navController)
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        FriendListScreen(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun FabMenu(
    navController: NavController
) {
    var expanded by remember { mutableStateOf(false) }

    val distance = 72.dp

    val topOffset by animateDpAsState(
        targetValue = if (expanded) -distance else 0.dp,
        label = "top"
    )

    val leftOffset by animateDpAsState(
        targetValue = if (expanded) -distance else 0.dp,
        label = "left"
    )

    val rightOffset by animateDpAsState(
        targetValue = if (expanded) distance else 0.dp,
        label = "right"
    )

    val alpha by animateFloatAsState(
        targetValue = if (expanded) 1f else 0f,
        label = "alpha"
    )

    Box(
        contentAlignment = Alignment.Center
    ) {

        SmallFloatingActionButton(
            onClick = {
            /* Map */
                navController.navigate("map")
            },
            modifier = Modifier
                .offset(y = topOffset)
                .alpha(alpha)
        ) {
            Icon(Icons.Default.LocationOn, null)
        }

        SmallFloatingActionButton(
            onClick = { /* Edit */
                navController.navigate("profile")
            },
            modifier = Modifier
                .offset(x = leftOffset)
                .alpha(alpha)
        ) {
            Icon(Icons.Default.Edit, null)
        }

        SmallFloatingActionButton(
            onClick = { /* Logout */ },
            modifier = Modifier
                .offset(x = rightOffset)
                .alpha(alpha)
        ) {
            Icon(Icons.Default.ExitToApp, null)
        }

        FloatingActionButton(
            onClick = { expanded = !expanded }
        ) {
            Icon(
                if (expanded) Icons.Default.Close else Icons.Default.Menu,
                null
            )
        }
    }
}

