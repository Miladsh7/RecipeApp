package com.msh.recipapp.data.database.entity.more

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.msh.recipapp.models.home.ResponseVideo
import com.msh.recipapp.utils.MORE_VIDEO_TABLE_NAME

@Entity(tableName = MORE_VIDEO_TABLE_NAME)
data class MoreVideoEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var response: ResponseVideo
)
