package com.msh.recipapp.data.database.entity.bookmark

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.msh.recipapp.models.detail.ResponseDetail
import com.msh.recipapp.utils.RECIPE_BOOKMARK_TABLE_NAME

@Entity(tableName = RECIPE_BOOKMARK_TABLE_NAME)
data class RecipeBookMarkEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val result: ResponseDetail
)
