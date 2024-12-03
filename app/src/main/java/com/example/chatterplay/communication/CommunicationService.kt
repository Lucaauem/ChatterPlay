package com.example.chatterplay.communication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    private val retrofitTypicode: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.56.1:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val userActivityApi: UserActivityApi by lazy {
        retrofitTypicode.create(UserActivityApi::class.java)
    }

    val userActivityRepository: UserActivityRepository by lazy {
        UserActivityRepository(userActivityApi)
    }
}
