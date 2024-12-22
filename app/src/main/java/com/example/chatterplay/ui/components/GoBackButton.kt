package com.example.chatterplay.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chatterplay.ui.activities.AppActivity

class GoBackButton(private val modifier: Modifier) {

    @Composable
    fun Render(activity: AppActivity) {
        IconButton (
            onClick = { activity.finish() },
            modifier = this.modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Go back"
            )
        }
    }
}