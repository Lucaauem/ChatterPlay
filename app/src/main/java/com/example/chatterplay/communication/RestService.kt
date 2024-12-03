package com.example.chatterplay.communication

import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class UserLogin (
    val id: String
)

interface UserActivityApi {
    @POST("user")
    suspend fun loginUser(@Body body: UserLogin) : Int
    @GET("chatroom")
    suspend fun getChatrooms(): List<String>
}

class UserActivityRepository(private val userActivityApi: UserActivityApi) {
    suspend fun fetchChatrooms() = userActivityApi.getChatrooms()
    suspend fun loginUser(data: UserLogin) = userActivityApi.loginUser(data)
}

class RestService {
    companion object {
        private var instance: RestService? = null

        fun getInstance() : RestService {
            if (instance == null) {
                instance = RestService()
            }
            return instance as RestService
        }
    }

    private val repository: UserActivityRepository = ServiceLocator.userActivityRepository

    suspend fun loadChatrooms(): JSONObject {
        val json = JSONObject(repository.fetchChatrooms()[0])
        return json
    }

    suspend fun login(id: String): Int {
        val port = repository.loginUser(UserLogin(id))
        return port
    }
}
