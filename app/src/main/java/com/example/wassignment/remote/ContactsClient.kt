package com.example.wassignment.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ContactsClient {


    companion object {
        private const val BASE_URL = "https://iie-service-dev.workingllama.com/"
        private var retrofit: Retrofit? = null

        fun getClient(): Retrofit {
            when (retrofit) {
                null -> retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit as Retrofit
        }
    }


}