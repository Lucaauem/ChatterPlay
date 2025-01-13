package com.example.chatterplay.communication

import android.util.Log
import com.example.chatterplay.UserSession
import com.example.chatterplay.chat.ChatMessage
import com.example.chatterplay.ui.activities.games.GameActivities
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

data class GameInvitation (
    val oponentId: String,
    val creatorId: String,
    val gameType: String
)

data class UserData (
    val firstName: String,
    val lastName: String,
    val origin: String,
    val joined: String,
    val messages: Int
)

data class UserUpdate (
    val id: String,
    val firstName: String,
    val lastName: String,
    val origin: String
)

interface RestApi {
    @GET("test")
    suspend fun testConnection()
    @GET("user")
    suspend fun getUser(@Query("id") userId: String) : UserData
    @POST("user")
    suspend fun loginUser(@Body body: UserLogin) : Int
    @PUT("user")
    suspend fun updateUser(@Body body: UserUpdate)
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
    @POST("game")
    suspend fun inviteToGame(@Body body: GameInvitation)
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

        suspend fun testConnection(ipAddress: String) {
            val tmpService = Retrofit.Builder()
                .baseUrl("http://$ipAddress:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RestApi::class.java)
            tmpService.testConnection()
        }
    }
    private val api: RestApi = ServiceLocator.restApi

    suspend fun getUser(id: String) : UserData { return api.getUser(id) }

    suspend fun updatePlayer(data: UserUpdate) { api.updateUser(data) }

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

    suspend fun inviteToGame(oponentId: String, selectedGame: GameActivities) {
        val creatorId = UserSession.getInstance().user!!.id
        val invitation = GameInvitation(oponentId, creatorId, selectedGame.toString())

        api.inviteToGame(invitation)
    }
}
