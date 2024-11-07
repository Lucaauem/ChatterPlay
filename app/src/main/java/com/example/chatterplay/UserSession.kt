package com.example.chatterplay

import com.example.chatterplay.chat.Chatroom

class UserSession {
    var chats = HashMap<String, Chatroom>()
        private set
    var currentChat: Chatroom? = null
        private set

    fun start() {
        this.loadRooms()
    }

    private fun loadRooms() {
        // !TODO! Get chats via userId from db
        val d_CHAT_ID = "cr_12345"
        val d_CHAT_ID_2 = "cr_12346"
        this.chats[d_CHAT_ID] = Chatroom(d_CHAT_ID)
        this.chats[d_CHAT_ID_2] = Chatroom(d_CHAT_ID_2)
    }

    fun joinChat(id: String) {
        if(this.chats[id] != null) {
            this.currentChat = this.chats[id]
        }
    }
}