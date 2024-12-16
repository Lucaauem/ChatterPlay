package com.example.chatterplay.communication

import com.example.chatterplay.UserSession
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

data class SendMessage (
    val chatId: String,
    val senderId: String,
    val content: String
)

interface RestApi {
    @POST("user")
    suspend fun loginUser(@Body body: UserLogin) : Int
    @GET("chatroom")
    suspend fun getChatrooms(@Query("userId") userId : String): List<String>
    @GET("message")
    suspend fun getMessages(@Query("chat") chatId: String) : List<Message>
    @POST("message")
    suspend fun sendMessage(@Body body: SendMessage)
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
    private val api: RestApi = ServiceLocator.restApi

    suspend fun loadChatrooms(): JSONObject { return JSONObject(api.getChatrooms(UserSession.getInstance().user!!.id)[0]) }

    suspend fun login(id: String): Int { return api.loginUser(UserLogin(id)) }

    suspend fun loadMessages(chatId: String): ArrayList<ChatMessage> {
        val rawMessages = api.getMessages(chatId)
        val messageList: ArrayList<ChatMessage> = ArrayList()

        rawMessages.forEach {
            val message = ChatMessage(it.id, it.sender, it.senderName, it.content)
            messageList.add(message)
        }

        return messageList
    }

    suspend fun sendMessage(chatId: String, clientId: String, content: String) {
        val message = SendMessage(chatId, clientId, content)
        api.sendMessage(message)
    }
}
