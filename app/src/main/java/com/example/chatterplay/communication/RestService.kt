package com.example.chatterplay.communication

import android.util.Log
import com.example.chatterplay.UserSession
import com.example.chatterplay.chat.ChatMessage
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import java.sql.Timestamp

data class UserLogin (
    val id: String
)

data class Message (
    val id: String,
    val sender: String,
    val senderName: String,
    val chat: String,
    val content: String,
    val timestamp: Long
)

data class SendMessage (
    val chatId: String,
    val senderId: String,
    val content: String
)

data class CreateChatroom (
    val name: String,
    val creator: String
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
    @POST("chatroom")
    suspend fun createChatroom(@Body body : CreateChatroom)
    @PUT("chatroom")
    suspend fun joinChatroom(@Query("user") userId: String, @Query("chatroom") chatId: String) : Boolean
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
            val message = ChatMessage(it.id, it.sender, it.senderName, it.content, Timestamp(it.timestamp))
            messageList.add(message)
        }

        messageList.sortBy { it.timestamp }
        return messageList
    }

    suspend fun sendMessage(chatId: String, clientId: String, content: String) {
        val message = SendMessage(chatId, clientId, content)
        api.sendMessage(message)
    }

    suspend fun createChatroom(name: String) {
        val chatroom = CreateChatroom(name, UserSession.getInstance().user!!.id)
        api.createChatroom(chatroom)
    }

    suspend fun joinChatroom(chatId: String) : Boolean {
        val status = api.joinChatroom(UserSession.getInstance().user!!.id, chatId)
        Log.i("DEBUG", status.toString())
        return status
    }
}
