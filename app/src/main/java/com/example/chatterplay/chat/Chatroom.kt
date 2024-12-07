package com.example.chatterplay.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.chatterplay.UserSession
import com.example.chatterplay.communication.RestService
import com.example.chatterplay.user.User
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class Chatroom(id: String, name: String) {
    var name: String = name
        private set
    var id: String = id
        private set
    private var users = ArrayList<User>()
    private val messages = ArrayList<ChatMessage>()

    init {
        this.loadRoomData()
        this.loadRoomMessages()
    }

    private fun loadRoomData() {
        // !TODO! Connect to database and load config data
        //this.users.add(User(0))
        //this.users.add(User(1))
        //this.users.add(User(2))
    }

    private fun loadRoomMessages() {
        runBlocking {
            val req = async { RestService.getInstance().loadMessages(id) }
            val messageList = req.await()

            messageList.forEach { messages.add(it) }
        }
    }

    fun addMessage(message: ChatMessage) {
        this.messages.add(message)
    }

    @Composable
    fun Render(modifier: Modifier) {
        val messages = this.messages
        val chatroomName = this.name
        val memberCount = this.users.count()

        var textInput by remember { mutableStateOf("") }

        Box(modifier = Modifier.fillMaxHeight(1f)) {
            Column {
                Row(modifier = modifier.zIndex(10f)) {
                    Column(modifier = Modifier
                        .background(Color(64, 127, 183))
                        .padding(vertical = 3.dp)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(1f),
                            color =  Color.White,
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            text = chatroomName,
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(1f),
                            color = Color.White,
                            fontSize = 13.sp,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center,
                            text = "(${memberCount} Mitglieder)"
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxHeight(0.90f)) {
                    RenderMessages(messages, modifier)
                }
                Row(modifier = Modifier.fillMaxHeight(1f).padding(start = 10.dp, bottom = 50.dp), verticalAlignment = Alignment.Bottom) {
                    Column {
                        TextField(
                            modifier = Modifier.fillMaxWidth(0.75f),
                            value = textInput,
                            onValueChange = { textInput = it },
                            label = { Text("Nachricht eingeben...") }
                        )
                    }
                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Button(onClick = { sendMessage(textInput); textInput = "" }) {
                            Text("Send")
                        }
                    }
                }
            }
        }
    }

    private fun sendMessage(input: String) {
        val userName = UserSession.getInstance().user!!.firstName
        this.addMessage(ChatMessage("0000", UserSession.getInstance().user!!.id, userName, input))
    }

    @Composable
    private fun RenderMessages(messages: ArrayList<ChatMessage>, modifier: Modifier) {
        val id = UserSession.getInstance().user!!.id

        Box {
           LazyVerticalGrid(
               columns = GridCells.Fixed(1),
               verticalArrangement = Arrangement.SpaceEvenly,
           ) {
               items(messages.count()) { index ->
                   messages[index].ShowMessage(modifier, messages[index].isOwnMessage(id))
               }
           }
        }
    }
}
