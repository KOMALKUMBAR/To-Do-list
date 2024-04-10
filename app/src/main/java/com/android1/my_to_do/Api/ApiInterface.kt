package com.android1.my_to_do.Api

import com.android1.my_to_do.model.JsonData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("todos")
    fun getJson(): Call<JsonData>
}