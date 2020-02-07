package com.example.myapplication.api

import com.example.myapplication.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

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

    @FormUrlEncoded
    @POST("update/tunda")
    fun updateTunda(
        @Field("id") id: String,
        @Field("fruitName") fruitName: String,
        @Field("fruitPrice") fruitPrice: String
    ): Call<MatundaResponse>

    @FormUrlEncoded
    @POST("delete/tunda")
    fun deleteTunda(
        @Field("id") id: String
    ): Call<MatundaResponse>

    @Multipart
    @POST("upload/image")
    fun uploadImage(@Part file: MultipartBody.Part,
                    @Part("description") imgDescription: RequestBody,
                    @Part("firstName") fName: RequestBody,
                    @Part("lastName") lName: RequestBody,
                    @Part("email") email: RequestBody,
                    @Part("password") password: RequestBody
                    ): Call<StatusResponse>
}