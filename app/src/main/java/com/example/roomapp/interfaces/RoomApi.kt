package com.example.roomapp.interfaces

import com.example.roomapp.data.Data
import retrofit2.Response
import retrofit2.http.GET

interface RoomApi {
    @GET("db")
    suspend fun getFacilities() : Response<Data>
}