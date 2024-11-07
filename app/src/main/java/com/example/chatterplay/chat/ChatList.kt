package com.example.chatterplay.chat

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.chatterplay.UserSession

class ChatList(private val session: UserSession) {
    @Composable
    fun LoadChats() {
        val chatIds: Array<String> = this.session.chats.keys.toTypedArray()
        LazyVerticalGrid(columns = GridCells.Fixed(1)) {
            items(chatIds.size) { index ->
                LoadChat(chatIds[index])
            }
        }
    }

    @Composable
    private fun LoadChat(id: String) {
        // !TODO! Button to join chat
        Text(text = id)
    }
}