package com.example.chatterplay.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class Message(sender: String, content: String) {
    var sender = sender
        private set
    var content = content
        private set

    @Composable
    fun ShowMessage(modifier: Modifier) {
        val boxModifier = modifier
            .background(color = Color.Blue)
            .padding(horizontal = 10.dp, vertical = 5.dp)

        Box (modifier = boxModifier){
            Row {
                Column {
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