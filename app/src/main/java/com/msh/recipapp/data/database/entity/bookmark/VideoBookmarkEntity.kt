package com.msh.recipapp.data.database.entity.bookmark

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.msh.recipapp.models.home.ResponseVideo
import com.msh.recipapp.utils.VIDEO_BOOKMARK_TABLE_NAME

@Entity(tableName = VIDEO_BOOKMARK_TABLE_NAME)
data class VideoBookmarkEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val result: ResponseVideo.Video
)