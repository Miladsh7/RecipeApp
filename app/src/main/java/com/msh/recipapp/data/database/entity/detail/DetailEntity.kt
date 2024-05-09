package com.msh.recipapp.data.database.entity.detail

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.msh.recipapp.models.detail.ResponseDetail
import com.msh.recipapp.utils.DETAIL_TABLE_NAME

@Entity(tableName = DETAIL_TABLE_NAME)
data class DetailEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val result: ResponseDetail
)
