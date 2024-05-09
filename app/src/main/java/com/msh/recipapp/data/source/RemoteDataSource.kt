package com.msh.recipapp.data.source

import com.msh.recipapp.data.network.ApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: ApiService
) {
    suspend fun getVideoTrending(queries: Map<String, String>) = api.getVideoTrending(queries)
    suspend fun getPopular(queries: Map<String, String>) = api.getPopular(queries)
    suspend fun getRecent(queries: Map<String, String>) = api.getRecent(queries)
    suspend fun getDetail(id: Int, apiKey: String) = api.getDetail(id, apiKey)
}