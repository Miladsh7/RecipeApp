package com.msh.recipapp.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.msh.recipapp.models.detail.ResponseDetail
import com.msh.recipapp.models.home.ResponsePopular
import com.msh.recipapp.models.home.ResponseRecent
import com.msh.recipapp.models.home.ResponseVideo

class RecipeAppTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun videoToJson(video: ResponseVideo): String {
        return gson.toJson(video)
    }

    @TypeConverter
    fun stringToVideo(data: String): ResponseVideo {
        return gson.fromJson(data, ResponseVideo::class.java)
    }

    @TypeConverter
    fun popularToJson(popular: ResponsePopular): String {
        return gson.toJson(popular)
    }

    @TypeConverter
    fun stringToPopular(data: String): ResponsePopular {
        return gson.fromJson(data, ResponsePopular::class.java)
    }

    @TypeConverter
    fun recentToJson(recent: ResponseRecent): String {
        return gson.toJson(recent)
    }

    @TypeConverter
    fun stringToRecent(data: String): ResponseRecent {
        return gson.fromJson(data, ResponseRecent::class.java)
    }

    @TypeConverter
    fun videoBookmarkToJson(video: ResponseVideo.Video): String {
        return gson.toJson(video)
    }

    @TypeConverter
    fun stringToVideoBookmark(data: String): ResponseVideo.Video {
        return gson.fromJson(data, ResponseVideo.Video::class.java)
    }

    @TypeConverter
    fun detailToJson(recipe: ResponseDetail): String {
        return gson.toJson(recipe)
    }

    @TypeConverter
    fun stringToDetail(data: String): ResponseDetail {
        return gson.fromJson(data, ResponseDetail::class.java)
    }

}