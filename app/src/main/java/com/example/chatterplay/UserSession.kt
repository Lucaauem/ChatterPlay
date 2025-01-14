package com.example.chatterplay

import com.example.chatterplay.game.FourConnect
import com.example.chatterplay.chat.Chatroom
import com.example.chatterplay.communication.RestService
import com.example.chatterplay.communication.socket.SocketSerivce
import com.example.chatterplay.game.Game
import com.example.chatterplay.game.GameMode
import com.example.chatterplay.game.TicTacToe
import com.example.chatterplay.ui.activities.games.GameActivities
import com.example.chatterplay.user.User
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class UserSession private constructor() {
    companion object {
        private var instance: UserSession? = null
        lateinit var IP : String

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
    var selectedGame: Game? = null
    var selectedGameAcitvity: GameActivities? = null
    var currentGameId: String = ""

    var mainActivity: MainActivity? = null

    fun logIn(user: User) : Boolean {
        var canConnect = true

        runBlocking {
            val req = async { RestService.getInstance().login(user.id) }
            val socketPort = req.await()

            canConnect = socketPort != -1

            if(canConnect) {
                val socketServce = SocketSerivce(socketPort)
                socketServce.start()
            }
        }

        if(canConnect) {
            this.user = user
            this.loadChatRooms()
        }

        return canConnect
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

    fun refreshChatlist() {
        this.loadChatRooms()
    }

    fun openGame(mode: GameMode, playerId: Int) {
        this.selectedGame = when(this.selectedGameAcitvity!!) {
            GameActivities.CONNECT_FOUR -> FourConnect(mode, playerId)
            GameActivities.TIC_TAC_TOE -> TicTacToe(mode, playerId)
        }
    }
}