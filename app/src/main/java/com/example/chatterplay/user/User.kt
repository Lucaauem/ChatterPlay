package com.example.chatterplay.user

import java.util.Date


class User(id: String) {
    var firstName: String = ""
        private set
    var id: String = id
        private set
    private var lastName = ""
    private var totalMessages = 0
    private var origin: String? = null
    private var joinDate = Date()

    init {
        loadData()
    }

    private fun loadData() {
        // !TODO! Connect to database and load user data
        this.firstName = "Luca"
        this.lastName = "Außem"
        this.joinDate = Date()
        this.origin = "Düren"
        this.totalMessages = 10
    }
}