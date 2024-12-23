package com.example.chatterplay.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatterplay.ui.activities.AppActivity

class CpButtons {
    companion object {
        @Composable
        fun CpIconButton(modifier: Modifier = Modifier, icon: ImageVector,description: String, onClick: () -> Unit) {
            CpIconButtonClass(modifier = modifier, icon = icon, description = description, onClick = onClick).Render()
        }

        @Composable
        fun CpGoBackButton(modifier : Modifier = Modifier, activity: AppActivity) {
            GoBackButton(modifier = modifier, activity = activity).Render()
        }

        @Composable
        fun CpBigButton(text: String, onClick: () -> Unit, enabled: Boolean = true) {
            Button(
                enabled = enabled,
                onClick = { onClick() },
                modifier = Modifier.fillMaxWidth(0.65f),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = text,
                    modifier = Modifier.padding(vertical = 5.dp),
                    fontSize = 27.sp
                )
            }
        }
    }
}