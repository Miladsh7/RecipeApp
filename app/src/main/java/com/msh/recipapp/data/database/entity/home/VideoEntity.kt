package com.msh.recipapp.data.database.entity.home

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.msh.recipapp.models.home.ResponseVideo
import com.msh.recipapp.utils.VIDEO_TABLE_NAME

@Entity(tableName = VIDEO_TABLE_NAME)
data class VideoEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var response: ResponseVideo
)
