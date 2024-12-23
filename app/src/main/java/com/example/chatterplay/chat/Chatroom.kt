package com.example.chatterplay.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
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
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class Chatroom(id: String, name: String) {
    var name: String = name
        private set
    var id: String = id
        private set
    private val messages = mutableStateOf(listOf<ChatMessage>())

    init {
        this.loadRoomMessages()
    }

    private fun loadRoomMessages() {
        runBlocking {
            val req = async { RestService.getInstance().loadMessages(id) }
            val messageList = req.await()

            val tmpList = messages.value.toMutableList()
            messageList.forEach {
                tmpList.add(it)
            }
            messages.value = tmpList
        }
    }

    fun addMessage(message: ChatMessage) {
        val tmpList = this.messages.value.toMutableList()
        tmpList.add(message)
        this.messages.value = tmpList
    }

    @Composable
    fun Render() {
        val messages by remember { this.messages }
        var textInput by remember { mutableStateOf("") }

        Box(modifier = Modifier.fillMaxHeight(1f)) {
            Column {
                Row(modifier = Modifier.zIndex(10f)) {
                    StatusBar(name)
                }
                Row(modifier = Modifier.fillMaxHeight(0.87f)) {
                    RenderMessages(messages)
                }
                Row(
                    modifier = Modifier
                        .fillMaxHeight(1f)
                        .padding(start = 10.dp, end = 10.dp, bottom = 50.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(0.75f),
                        value = textInput,
                        onValueChange = { textInput = it },
                        label = { Text("Nachricht eingeben...") }
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(0.75f),
                        onClick = { sendMessage(textInput); textInput = "" },
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Text("Send")
                    }
                }
            }
        }
    }

    private fun sendMessage(input: String) {
        runBlocking {
            val req = async { RestService.getInstance().sendMessage(id, UserSession.getInstance().user!!.id, input) }
            req.await()
        }
    }

    @Composable
    private fun RenderMessages(messages: List<ChatMessage>) {
        val id = UserSession.getInstance().user!!.id
        var previousId = ""

        Box {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                verticalArrangement = Arrangement.spacedBy(0.dp),
            ) {
                items(messages.count()) { index ->
                    messages[index].ShowMessage(messages[index].isOwnMessage(id), previousId == messages[index].senderId)
                    previousId = messages[index].senderId
                }
            }
        }
    }

    @Composable
    private fun StatusBar(chatroomName : String) {
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
                text = "[${id}]"
            )
        }
    }
}
