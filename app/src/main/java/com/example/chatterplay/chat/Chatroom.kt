package com.example.chatterplay.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class Chatroom {
    private val modifier: Modifier;
    private val messages = ArrayList<ChatMessage>()

    constructor(modifier: Modifier) {
        this.modifier = modifier
        this.addMessage(ChatMessage("Luca", "Hi!!!"))
        this.addMessage(ChatMessage("Viktor", "Moin\nIch bin der Viktor!"))
        this.addMessage(ChatMessage("Maaran", "Hallo."))
    }

    fun addMessage(message: ChatMessage) {
        this.messages.add(message)
    }

    @Composable
    fun RenderMessages() {
        val messages = this.messages
        val modifier = this.modifier
        Box {
           LazyVerticalGrid(
               columns = GridCells.Fixed(1),
               verticalArrangement = Arrangement.SpaceEvenly,
           ) {
               items(messages.count()) { index ->
                   // !TODO! Check if own message
                   messages[index].ShowMessage(modifier, false)
               }
           }
        }
    }
}