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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatterplay.ui.theme.RwthBlueDark
import java.sql.Timestamp

class ChatMessage(private var id: String, sender: String, private var senderName: String, private var content: String, val timestamp: Timestamp) {
    private var senderId = sender

    fun isOwnMessage(id: String) : Boolean {
        return this.senderId == id
    }

    @Composable
    fun ShowMessage(isOwnMessage: Boolean) {
        val edgeMargin = 7.dp
        val boxModifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color = RwthBlueDark)
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .width(225.dp)

        Box{
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (isOwnMessage) Arrangement.End else Arrangement.Start) {
                Column {
                    Spacer(modifier = Modifier.width(edgeMargin))
                }
                Column(modifier = boxModifier) {
                    Text(
                        text = senderName,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                    Text(
                        text = content,
                        color = Color.White
                    )
                }
                Column {
                    Spacer(modifier = Modifier.width(edgeMargin))
                }
            }
        }
    }
}