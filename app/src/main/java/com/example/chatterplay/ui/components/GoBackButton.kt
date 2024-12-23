package com.example.chatterplay.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.chatterplay.ui.activities.AppActivity

class GoBackButton(modifier: Modifier, private val activity: AppActivity) :
    CpIconButtonClass(modifier = modifier, icon = ICON, description = DESCRIPTION, onClick = { activity.finish() }) {

        companion object {
        private val ICON : ImageVector = Icons.AutoMirrored.Filled.ArrowBack
        private const val DESCRIPTION : String = "Go back"
    }
}