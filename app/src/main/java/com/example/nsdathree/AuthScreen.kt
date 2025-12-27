package com.example.nsdathree

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun AuthScreen(
    navController: NavController
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
    navController: NavController
){
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
                navController.navigate("main")
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun RegPart(
    navController: NavController
){
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
                navController.navigate("main")
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}