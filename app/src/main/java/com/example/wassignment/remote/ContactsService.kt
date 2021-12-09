package com.example.wassignment.remote

import com.example.wassignment.models.Contacts
import com.example.wassignment.models.star.StarredItem
import retrofit2.Response
import retrofit2.http.*

interface ContactsService {


    @GET("/iie-service/v1/contacts")
    suspend fun fetchContacts(
        @Query("pageNumber") page:Int
    ): Response<Contacts>

    @POST("/iie-service/v1/star/{id}")
    suspend fun postStar(@Path("id") id : Int) : Response<StarredItem>

    @POST("/iie-service/v1/unstar/{id}")
    suspend fun postUnStar(@Path("id") id : Int) : Response<StarredItem>

}