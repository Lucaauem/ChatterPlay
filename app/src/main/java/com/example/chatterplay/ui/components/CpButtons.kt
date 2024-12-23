package com.example.chatterplay.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.chatterplay.ui.activities.AppActivity

class CpButtons {
    companion object {
        @Composable
        fun CpIconButton(modifier: Modifier = Modifier,icon : ImageVector,description: String, onClick: () -> Unit) {
            CpIconButtonClass(modifier = modifier, icon = icon, description = description, onClick = onClick).Render()
        }

        @Composable
        fun CpGoBackButton(modifier : Modifier = Modifier, activity: AppActivity) {
            GoBackButton(modifier = modifier, activity = activity).Render()
        }
    }
}