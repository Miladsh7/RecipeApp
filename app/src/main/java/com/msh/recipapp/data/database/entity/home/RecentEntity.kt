package com.msh.recipapp.data.database.entity.home

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.msh.recipapp.models.home.ResponseRecent
import com.msh.recipapp.utils.RECENT_TABLE_NAME

@Entity(tableName = RECENT_TABLE_NAME)
data class RecentEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var response: ResponseRecent
)
