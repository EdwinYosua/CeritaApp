package com.edwinyosua.ceritaapp.network

import com.edwinyosua.ceritaapp.network.apiresponse.DetailResponse
import com.edwinyosua.ceritaapp.network.apiresponse.LoginResponse
import com.edwinyosua.ceritaapp.network.apiresponse.RegisterResponse
import com.edwinyosua.ceritaapp.network.apiresponse.StoriesResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): Response<StoriesResponse>

    @GET("stories/{id}")
    suspend fun getDetailStories(
        @Path("id") id: String
    ): DetailResponse

}