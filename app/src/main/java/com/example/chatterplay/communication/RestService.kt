package com.example.chatterplay.communication

import org.json.JSONObject
import retrofit2.http.GET

interface UserActivityApi {
    @GET("chatroom")
    suspend fun getChatrooms(): List<String>
}

class UserActivityRepository(private val userActivityApi: UserActivityApi) {
    suspend fun fetchChatrooms() = userActivityApi.getChatrooms()
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
}
