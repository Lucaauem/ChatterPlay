package com.example.chatterplay.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

open class CpIconButtonClass(private val modifier: Modifier = Modifier, private val icon : ImageVector, private val description: String, private val onClick: () -> Unit) {
    @Composable
    fun Render() {
        IconButton (
            onClick = { this.onClick() },
            modifier = this.modifier.size(40.dp)
        ) {
            Icon(
                imageVector = this.icon,
                contentDescription = this.description,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}