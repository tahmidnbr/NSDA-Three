package com.example.nsdathree.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.nsdathree.model.User
import com.example.nsdathree.repository.UserRepository

class UserViewModel() : ViewModel() {

    private val repository = UserRepository()

    var users by mutableStateOf<List<User>>(emptyList())
        private set
    var loading: Boolean = false
        private set

    fun saveUser(user: User) {
        repository.saveUser(user)
    }

    fun updateLocation(
        userId: String,
        latitude: Double,
        longitude: Double
    ) {
        repository.updateLocation(userId, latitude, longitude)
    }

    fun updateDisplayName(userId: String, newName: String) {
        repository.updateDisplayName(userId, newName)
        // Update locally too
        users = users.map {
            if (it.userId == userId) it.copy(displayName = newName) else it
        }
    }


    fun loadAllUsers(onResult: (List<User>) -> Unit = {}) {
        repository.getAllUsers { result ->
            users = result
            onResult(result)
        }
    }
}