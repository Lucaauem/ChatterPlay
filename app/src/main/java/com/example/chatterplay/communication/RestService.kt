package com.example.chatterplay.communication

import android.util.Log
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.http.GET

data class UserModel(
    val id: Int,
    val name: String,
    val email: String,
)

interface UserActivityApi {
    @GET("users")
    suspend fun getUsers(): List<UserModel>
}

class UserActivityRepository(private val userActivityApi: UserActivityApi) {
    suspend fun fetchUsers() = userActivityApi.getUsers()
}

class RestService {
    private val repository: UserActivityRepository = ServiceLocator.userActivityRepository
    private val _users = MutableStateFlow<List<UserModel>>(emptyList())

    @OptIn(DelicateCoroutinesApi::class)
    fun debug() {
        GlobalScope.launch {
            _users.value = repository.fetchUsers()
            Log.i("DEBUG", "" + _users.value[1].name)
        }
    }
}
