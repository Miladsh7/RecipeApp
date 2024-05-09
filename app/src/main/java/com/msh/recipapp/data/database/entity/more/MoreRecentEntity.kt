package com.msh.recipapp.data.database.entity.more

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.msh.recipapp.models.home.ResponseRecent
import com.msh.recipapp.utils.MORE_RECENT_TABLE_NAME

@Entity(tableName = MORE_RECENT_TABLE_NAME)
data class MoreRecentEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var response: ResponseRecent
)
