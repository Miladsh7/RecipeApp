package com.msh.recipapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.msh.recipapp.data.database.entity.home.PopularEntity
import com.msh.recipapp.data.database.entity.home.RecentEntity
import com.msh.recipapp.data.database.entity.home.VideoEntity
import com.msh.recipapp.data.database.entity.bookmark.RecipeBookMarkEntity
import com.msh.recipapp.data.database.entity.bookmark.VideoBookmarkEntity
import com.msh.recipapp.data.database.entity.detail.DetailEntity
import com.msh.recipapp.data.database.entity.more.MoreRecentEntity
import com.msh.recipapp.data.database.entity.more.MoreVideoEntity

@Database(
    entities = [
        VideoEntity::class, PopularEntity::class,
        RecentEntity::class, DetailEntity::class,
        RecipeBookMarkEntity::class, VideoBookmarkEntity::class,
        MoreVideoEntity::class, MoreRecentEntity::class
    ],
    version = 9,
    exportSchema = false
)
@TypeConverters(RecipeAppTypeConverter::class)
abstract class RecipeAppDatabase : RoomDatabase() {
    abstract fun dao(): RecipeAppDao
}