package com.example.myapplication

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ServerApi {

    @GET("api/v1/employees")
    fun getEmployees(): Call<DatumResponse>

    @GET("matunda/details")
    fun getMatunda(): Call<MatundaResponse>

    @GET("food/details")
    fun getFood(): Call<FoodResponse>

    @FormUrlEncoded
    @POST("login/user")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserResponse>
}