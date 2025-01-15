package com.example.chatterplay.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatterplay.ui.theme.RwthBlueDark
import java.sql.Timestamp

class ChatMessage(val id: String, sender: String, private val senderName: String, private var content: String, val timestamp: Timestamp) {
    val senderId = sender

    fun isOwnMessage(id: String) : Boolean {
        return this.senderId == id
    }

    @Composable
    fun ShowMessage(isOwnMessage: Boolean, isPreviousSender: Boolean) {
        val edgeMargin = 7.dp
        val boxModifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(color = RwthBlueDark)
            .padding(horizontal = 15.dp, vertical = 6.dp)
            .wrapContentWidth()
            .widthIn(max = 225.dp)

        Box(modifier = Modifier.padding(top = if (isPreviousSender) 0.dp else 9.dp)){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                horizontalArrangement = if (isOwnMessage) Arrangement.End else Arrangement.Start
            ) {
                Column {
                    Spacer(modifier = Modifier.width(edgeMargin))
                }
                Column(modifier = boxModifier) {
                    MessageContent(isOwnMessage, isPreviousSender)
                }
                Column {
                    Spacer(modifier = Modifier.width(edgeMargin))
                }
            }
        }
    }

    @Composable
    fun MessageContent(isOwnMessage: Boolean, isPreviousSender: Boolean) {
        if(!(isOwnMessage || isPreviousSender)) {
            Text(
                text = senderName.ifEmpty { "Anonym" },
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontStyle = if (senderName.isEmpty()) FontStyle.Italic else FontStyle.Normal,
                fontSize = 17.sp
            )
        }
        Text(
            text = content,
            color = Color.White
        )
    }
}