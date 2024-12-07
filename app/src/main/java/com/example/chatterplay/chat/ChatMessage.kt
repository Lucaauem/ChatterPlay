package com.example.chatterplay.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class ChatMessage(private var id: String, sender: String, private var senderName: String, private var content: String) {
    private var senderId = sender

    fun isOwnMessage(id: String) : Boolean {
        return this.senderId == id
    }

    @Composable
    fun ShowMessage(modifier: Modifier, isOwnMessage: Boolean) {
        val boxModifier = modifier
            .background(color = Color.Blue)
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .width(200.dp)

        Box{
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (isOwnMessage) Arrangement.End else Arrangement.Start) {
                Column(modifier = boxModifier) {
                    Text(
                        text = senderName,
                        color = Color.White
                    )
                    Text(
                        text = content,
                        color = Color.White
                    )
                }
            }
        }
    }
}