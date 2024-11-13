package com.example.chatterplay

import com.example.chatterplay.chat.Chatroom
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

    fun init() {
        this.loadChatRooms()
    }

    fun logIn(user: User) {
        this.user = user
    }

    fun isLoggedIn() : Boolean {
        return this.user != null
    }

    private fun loadChatRooms() {
        // !TODO! Get chats via userId from db
        this.chats["cr_12345"] = Chatroom()
        this.chats["cr_12346"] = Chatroom()
    }

    fun joinChat(id: String) {
        if(this.chats[id] != null) {
            this.currentChat = this.chats[id]
        }
    }
}