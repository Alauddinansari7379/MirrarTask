package com.example.mirrartask.retrofit

import com.example.mirrartask.model.ModelDailyImage
import com.example.mirrartask.model.ModelImageOTD
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    @GET("planetary/apod")
    fun getImageOfTheDay(
        @Query("api_key") apiKey: String
    ): Call<ModelImageOTD>

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getDailyImage(
        @Query("page") page: String,
        @Query("sol") sol: String,
        @Query("api_key") apiKey: String
    ): Call<ModelDailyImage>

}