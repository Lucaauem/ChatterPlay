package com.example.chatterplay.user

import java.util.Date

class User {
    var id: Int = -1
        private set
    var firstName: String = ""
        private set
    private var lastName = ""
    private var totalMessages = 0
    private var origin: String? = null
    private var joinDate = Date()

    constructor(id: Int) {
        this.id = id
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