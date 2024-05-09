package com.msh.recipapp.data.database.entity.home

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.msh.recipapp.models.home.ResponsePopular
import com.msh.recipapp.utils.POPULAR_TABLE_NAME

@Entity(tableName = POPULAR_TABLE_NAME)
data class PopularEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var response: ResponsePopular,
)