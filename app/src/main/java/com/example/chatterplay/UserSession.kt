package com.example.chatterplay

import android.util.Log
import com.example.chatterplay.chat.ChatMessage
import com.example.chatterplay.chat.Chatroom
import com.example.chatterplay.connection.Client
import com.example.chatterplay.user.User

class UserSession private constructor() {
    companion object {
        private var instance: UserSession? = null

        fun getInstance() : UserSession {
            if (instance == null) {
                instance = UserSession()
            }
            return instance as UserSession
        }
    }

    var user: User? = null
        private set
    var currentChat : Chatroom? = null
        private set
    var chats = HashMap<String, Chatroom>()
        private set
    private lateinit var sessionClient: Client

    fun init() {
        this.loadChatRooms()
    }

    fun logIn(user: User) {
        this.user = user
        this.sessionClient = Client()
        this.sessionClient.start()
    }

    fun isLoggedIn() : Boolean {
        return this.user != null
    }

    private fun loadChatRooms() {
        // !TODO! Get chats via userId from db
        this.chats["cr_12345"] = Chatroom(12345)
        this.chats["cr_12346"] = Chatroom(12346)
    }

    fun joinChat(id: String) {
        if(this.chats[id] != null) {
            this.currentChat = this.chats[id]
        }
    }

    fun sendMessage(message: ChatMessage) {
        Log.i("DEBUG", "SEND;${message.chatId};${message.content}")
        this.sessionClient.sendMessage("SEND;${message.chatId};${message.content}")
    }
}