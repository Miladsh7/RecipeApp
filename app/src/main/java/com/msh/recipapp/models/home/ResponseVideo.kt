package com.msh.recipapp.models.home


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ResponseVideo(
    @SerializedName("totalResults")
    val totalResults: Int?,
    @SerializedName("videos")
    val videos: List<Video?>?
) {
    @Parcelize
    data class Video(
        @SerializedName("length")
        val length: Int?,
        @SerializedName("rating")
        val rating: Double?,
        @SerializedName("shortTitle")
        val shortTitle: String?,
        @SerializedName("thumbnail")
        val thumbnail: String?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("views")
        val views: Int?,
        @SerializedName("youTubeId")
        val youTubeId: String?
    ) : Parcelable
}