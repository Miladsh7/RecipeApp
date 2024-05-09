package com.msh.recipapp.data.network

import com.msh.recipapp.models.detail.ResponseDetail
import com.msh.recipapp.models.home.ResponsePopular
import com.msh.recipapp.models.home.ResponseRecent
import com.msh.recipapp.models.home.ResponseVideo
import com.msh.recipapp.utils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {

    @GET("food/videos/search")
    suspend fun getVideoTrending(@QueryMap queries: Map<String, String>): Response<ResponseVideo>

    @GET("recipes/complexSearch")
    suspend fun getPopular(@QueryMap queries: Map<String, String>): Response<ResponsePopular>

    @GET("recipes/complexSearch")
    suspend fun getRecent(@QueryMap queries: Map<String, String>): Response<ResponseRecent>

    @GET("recipes/{id}/information")
    suspend fun getDetail(@Path("id") id:Int, @Query(API_KEY) apiKey:String):Response<ResponseDetail>
}