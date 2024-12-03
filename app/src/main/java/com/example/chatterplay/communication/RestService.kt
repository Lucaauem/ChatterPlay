package com.example.chatterplay.communication

import com.example.chatterplay.chat.ChatMessage
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class UserLogin (
    val id: String
)

data class Message (
    val id: String,
    val sender: String,
    val senderName: String,
    val chat: String,
    val content: String
)

interface UserActivityApi {
    @POST("user")
    suspend fun loginUser(@Body body: UserLogin) : Int
    @GET("chatroom")
    suspend fun getChatrooms(): List<String>
    @GET("message")
    suspend fun getMessages(@Query("chat") chatId: String) : List<Message>
}

class UserActivityRepository(private val userActivityApi: UserActivityApi) {
    suspend fun fetchChatrooms() = userActivityApi.getChatrooms()
    suspend fun loginUser(data: UserLogin) = userActivityApi.loginUser(data)
    suspend fun getMessages(chatId: String) = userActivityApi.getMessages(chatId)
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

    suspend fun loadMessages(chatId: String): ArrayList<ChatMessage> {
        val rawMessages = repository.getMessages(chatId)
        val messageList: ArrayList<ChatMessage> = ArrayList()

        rawMessages.forEach {
            val message = ChatMessage(it.id, it.sender, it.senderName, it.content)
            messageList.add(message)
        }

        return messageList
    }
}
