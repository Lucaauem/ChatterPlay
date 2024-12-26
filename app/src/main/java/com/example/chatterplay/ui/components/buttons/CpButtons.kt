package com.example.chatterplay.ui.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatterplay.ui.activities.AppActivity
import com.example.chatterplay.ui.theme.RwthBlueDark

class CpButtons {
    companion object {
        @Composable
        fun CpIconButton(modifier: Modifier = Modifier, icon: ImageVector,description: String, onClick: () -> Unit) {
            CpIconButtonClass(modifier = modifier, icon = icon, description = description, onClick = onClick).Render()
        }

        @Composable
        fun CpIconBackgroundButton(modifier: Modifier = Modifier, icon: ImageVector,description: String, onClick: () -> Unit) {
            Button(
                onClick = { onClick() },
                modifier= modifier.size(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = RwthBlueDark),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
            ) {
                Icon(
                    icon,
                    contentDescription = description,
                    modifier = Modifier.size(25.dp)
                )
            }
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

        @Composable
        fun CpMediumButton(text: String, onClick: () -> Unit, enabled: Boolean = true) {
            Button(
                enabled = enabled,
                onClick = { onClick() },
                modifier = Modifier.fillMaxWidth(0.45f),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = text,
                    modifier = Modifier.padding(vertical = 5.dp),
                    fontSize = 20.sp
                )
            }
        }
    }
}