package com.example.chatterplay

import android.util.Log
import com.example.chatterplay.chat.Chatroom
import com.example.chatterplay.communication.RestService
import com.example.chatterplay.user.User
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class UserSession private constructor() {
    companion object {
        private var instance: UserSession? = null

        fun getInstance() : UserSession {
            if (instance == null) {
                instance = UserSession()
                instance!!.init()
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
        runBlocking {
            val req = async { RestService.getInstance().login(user.id) }
            val socketPort = req.await()

            // !TODO! Login with client service
        }
    }

    fun isLoggedIn() : Boolean {
        return this.user != null
    }

    private fun loadChatRooms() {
        runBlocking {
            val res = async { RestService.getInstance().loadChatrooms() }
            val json = res.await()

            json.keys().forEach { chats[it] = Chatroom(it, json.getString(it)) }
        }
    }

    fun joinChat(id: String) {
        if(this.chats[id] != null) {
            this.currentChat = this.chats[id]
        }
    }
}