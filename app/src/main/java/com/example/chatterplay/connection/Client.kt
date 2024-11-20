package com.example.chatterplay.connection

import android.util.Log
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket

class Client : Thread() {
    private lateinit var socket : Socket
    private var nextMessage = ""

    override fun run() {
        super.run()
        this.connectSocket()

        this.sendToServer("LOGIN;0")

        while (true) {
            if(this.nextMessage != "") {
                sendToServer(this.nextMessage)
                this.nextMessage = ""
            }
        }
    }

    private fun connectSocket() {
        this.socket = Socket()
        this.socket.connect(InetSocketAddress("172.26.144.1",8080),500)
    }

    fun sendMessage(message: String) {
        this.nextMessage = message
    }

    private fun sendToServer(message: String) {
        Log.i("DEBUGGING", message)
        this.socket.use {
            val writer = PrintWriter(OutputStreamWriter(it.getOutputStream()), true)
            writer.println(message)
        }
        this.connectSocket()
    }
}