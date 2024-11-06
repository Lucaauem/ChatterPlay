package com.example.chatterplay.chat

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
import androidx.compose.ui.unit.dp

class Chatroom(private val modifier: Modifier) {
    private val messages = ArrayList<ChatMessage>()

    init {
        this.addMessage(ChatMessage("Luca", "Hi!!!"))
        this.addMessage(ChatMessage("Viktor", "Moin\nIch bin der Viktor!"))
        this.addMessage(ChatMessage("Maaran", "Hallo."))
    }

    private fun addMessage(message: ChatMessage) {
        this.messages.add(message)
    }

    @Composable
    fun Render() {
        val messages = this.messages
        val modifier = this.modifier
        var textInput by remember { mutableStateOf("") }

        Box(modifier = Modifier.fillMaxHeight(1f)) {
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

    private fun sendMessage(input: String) {
        this.addMessage(ChatMessage("Viktor", input))
    }

    @Composable
    private fun RenderMessages(messages: ArrayList<ChatMessage>, modifier: Modifier) {
        val _USER_NAME = "Viktor" // !DEBUG!
        Box {
           LazyVerticalGrid(
               columns = GridCells.Fixed(1),
               verticalArrangement = Arrangement.SpaceEvenly,
           ) {
               items(messages.count()) { index ->
                   messages[index].ShowMessage(modifier, messages[index].isOwnMessage(_USER_NAME))
               }
           }
        }
    }
}
