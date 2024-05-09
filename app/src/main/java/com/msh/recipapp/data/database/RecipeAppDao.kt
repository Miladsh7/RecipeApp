package com.msh.recipapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.msh.recipapp.data.database.entity.bookmark.RecipeBookMarkEntity
import com.msh.recipapp.data.database.entity.bookmark.VideoBookmarkEntity
import com.msh.recipapp.data.database.entity.detail.DetailEntity
import com.msh.recipapp.data.database.entity.home.PopularEntity
import com.msh.recipapp.data.database.entity.home.RecentEntity
import com.msh.recipapp.data.database.entity.home.VideoEntity
import com.msh.recipapp.data.database.entity.more.MoreRecentEntity
import com.msh.recipapp.data.database.entity.more.MoreVideoEntity
import com.msh.recipapp.utils.DETAIL_TABLE_NAME
import com.msh.recipapp.utils.MORE_RECENT_TABLE_NAME
import com.msh.recipapp.utils.MORE_VIDEO_TABLE_NAME
import com.msh.recipapp.utils.POPULAR_TABLE_NAME
import com.msh.recipapp.utils.RECENT_TABLE_NAME
import com.msh.recipapp.utils.RECIPE_BOOKMARK_TABLE_NAME
import com.msh.recipapp.utils.VIDEO_BOOKMARK_TABLE_NAME
import com.msh.recipapp.utils.VIDEO_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeAppDao {

    //Video Trending
    @Insert(onConflict = REPLACE)
    suspend fun saveVideo(entity: VideoEntity)

    @Query("SELECT * FROM $VIDEO_TABLE_NAME ORDER BY ID ASC")
    fun loadVideo(): Flow<List<VideoEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun saveMoreVideo(entity: MoreVideoEntity)

    @Query("SELECT * FROM $MORE_VIDEO_TABLE_NAME ORDER BY ID ASC")
    fun moreLoadVideo(): Flow<List<MoreVideoEntity>>

    //Popular category
    @Insert(onConflict = REPLACE)
    suspend fun savePopular(entity: PopularEntity)

    @Query("SELECT * FROM $POPULAR_TABLE_NAME WHERE id = :id")
    fun loadPopular(id: Int): Flow<List<PopularEntity>>

    //Recent Recipe
    @Insert(onConflict = REPLACE)
    suspend fun saveRecent(entity: RecentEntity)

    @Query("SELECT * FROM $RECENT_TABLE_NAME ORDER BY ID ASC")
    fun loadRecent(): Flow<List<RecentEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun saveMoreRecent(entity: MoreRecentEntity)

    @Query("SELECT * FROM $MORE_RECENT_TABLE_NAME ORDER BY ID ASC")
    fun moreLoadRecent(): Flow<List<MoreRecentEntity>>

    //Detail
    @Insert(onConflict = REPLACE)
    suspend fun saveDetail(entity: DetailEntity)

    @Query("SELECT * FROM $DETAIL_TABLE_NAME WHERE id = :id")
    fun loadDetail(id: Int):Flow<DetailEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM $DETAIL_TABLE_NAME WHERE ID = :id)")
    fun existsDetail(id: Int): Flow<Boolean>

    //BookMark
    //Video
    @Insert(onConflict = REPLACE)
    suspend fun saveVideoBookMark(entity: VideoBookmarkEntity)

    @Delete
    suspend fun deleteVideoBookMark(entity: VideoBookmarkEntity)

    @Query("SELECT * FROM $VIDEO_BOOKMARK_TABLE_NAME ORDER BY id ASC")
    fun loadVideoBookMark(): Flow<List<VideoBookmarkEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM VIDEO_BOOKMARK_TABLE_NAME WHERE id = :id)")
    fun existsVideoBookMark(id: String): Flow<Boolean>

    //Recipe
    @Insert(onConflict = REPLACE)
    suspend fun saveRecipeBookMark(entity: RecipeBookMarkEntity)

    @Delete
    suspend fun deleteRecipeBookMark(entity: RecipeBookMarkEntity)

    @Query("SELECT * FROM $RECIPE_BOOKMARK_TABLE_NAME ORDER BY id ASC")
    fun loadRecipeBookMark(): Flow<List<RecipeBookMarkEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM RECIPE_BOOKMARK_TABLE_NAME WHERE id = :id)")
    fun existsRecipeBookMark(id: Int): Flow<Boolean>

}