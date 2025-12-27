package com.example.nsdathree.screens

import android.R.attr.shape
import android.util.Log
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nsdathree.model.User
import com.example.nsdathree.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendListScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = viewModel()
){
    LaunchedEffect(Unit) {
        userViewModel.loadAllUsers {
            Log.d("FriendList", "Users loaded")
        }
    }


    val currentUid = FirebaseAuth.getInstance().currentUser?.uid
    val users = userViewModel.users

    val currentUser = users.find { it.userId == currentUid }
    val friends = users.filter { it.userId != currentUid }

    Scaffold(
        topBar = {
            TopAppBar(title = {}, )
        }
    ) {innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            item { Text("My Profile", Modifier.padding(18.dp)) }

            currentUser?.let {
                item {
                    MyProfile(user = it)
                }
            }

            item { Text("Friends", Modifier.padding(18.dp)) }

            items(friends) { user ->
                FriendProfiles(user)
            }
        }

    }
}

@Composable
fun MyProfile(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(user.displayName?: "You", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(user.email)
        }
    }
}


@Composable
fun FriendProfiles(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                user.displayName ?: "Unnamed",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(user.email)
        }
    }
}
