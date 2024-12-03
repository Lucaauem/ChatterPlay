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

class ChatMessage(id: String, sender: String, senderName: String, content: String) {
    private var id = id
    private var sender = sender
    var senderName = senderName
        private set
    private var content = content

    fun isOwnMessage(userName: String) : Boolean {
        return this.sender == userName
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
                        text = sender,
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