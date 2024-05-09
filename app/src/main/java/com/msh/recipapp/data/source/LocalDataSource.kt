package com.msh.recipapp.data.source

import com.msh.recipapp.data.database.RecipeAppDao
import com.msh.recipapp.data.database.entity.home.PopularEntity
import com.msh.recipapp.data.database.entity.home.RecentEntity
import com.msh.recipapp.data.database.entity.home.VideoEntity
import com.msh.recipapp.data.database.entity.bookmark.RecipeBookMarkEntity
import com.msh.recipapp.data.database.entity.bookmark.VideoBookmarkEntity
import com.msh.recipapp.data.database.entity.detail.DetailEntity
import com.msh.recipapp.data.database.entity.more.MoreRecentEntity
import com.msh.recipapp.data.database.entity.more.MoreVideoEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: RecipeAppDao) {

    //Video Trending
    suspend fun saveVideos(entity: VideoEntity) = dao.saveVideo(entity)
    fun loadVideos() = dao.loadVideo()

    suspend fun moreSaveVideos(entity: MoreVideoEntity) = dao.saveMoreVideo(entity)
    fun moreLoadVideos() = dao.moreLoadVideo()

    //popular category
    suspend fun savePopular(entity: PopularEntity) = dao.savePopular(entity)

    fun loadPopular(id: Int) = dao.loadPopular(id)

    //Recent Recipe
    suspend fun saveRecent(entity: RecentEntity) = dao.saveRecent(entity)
    fun loadRecent() = dao.loadRecent()

    suspend fun saveMoreRecent(entity: MoreRecentEntity) = dao.saveMoreRecent(entity)
    fun moreLoadRecent() = dao.moreLoadRecent()

    //Detail
    suspend fun saveDetail(entity: DetailEntity) = dao.saveDetail(entity)
    fun loadDetail(id: Int) = dao.loadDetail(id)
    fun existsDetail(id: Int) = dao.existsDetail(id)

    //BookMark
    //Video
    suspend fun saveVideoBookMark(entity: VideoBookmarkEntity) = dao.saveVideoBookMark(entity)
    suspend fun deleteVideoBookMark(entity: VideoBookmarkEntity) = dao.deleteVideoBookMark(entity)
    fun loadVideoBookMark() = dao.loadVideoBookMark()
    fun existVideoBookMark(id: String) = dao.existsVideoBookMark(id)

    //Recipe
    suspend fun saveRecipeBookMark(entity: RecipeBookMarkEntity) = dao.saveRecipeBookMark(entity)
    suspend fun deleteRecipeBookMark(entity: RecipeBookMarkEntity) =
        dao.deleteRecipeBookMark(entity)

    fun loadRecipeBookMark() = dao.loadRecipeBookMark()
    fun existRecipeBookMark(id: Int) = dao.existsRecipeBookMark(id)
}