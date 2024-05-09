package com.msh.recipapp.models.home


import com.google.gson.annotations.SerializedName

data class ResponseRecent(
    @SerializedName("number")
    val number: Int?,
    @SerializedName("offset")
    val offset: Int?,
    @SerializedName("results")
    val results: List<Result?>?,
    @SerializedName("totalResults")
    val totalResults: Int?
) {
    data class Result(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("imageType")
        val imageType: String?,
        @SerializedName("title")
        val title: String?
    )
}