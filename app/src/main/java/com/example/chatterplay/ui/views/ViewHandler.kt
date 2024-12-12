package com.example.chatterplay.ui.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class ViewHandler {
    companion object {
        private var instance: ViewHandler? = null

        fun getInstance() : ViewHandler {
            if(instance == null) {
                instance = ViewHandler()
            }
            return instance as ViewHandler
        }
    }

    private var view: View = View.MAIN

    fun changeView(view: View) {
        this.view = view
    }

    @Composable
    fun CurrentView() {
        val currentView: MutableState<View> = remember { mutableStateOf(this.view) }

        when (currentView.value) {
            View.MAIN -> TODO()
            View.ACCOUNT -> TODO()
            View.CHAT_LIST -> TODO()
            View.CHAT -> TODO()
        }
    }
}