package com.msh.recipapp.data.repository

import com.msh.recipapp.data.source.LocalDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class VideoBookMarkRepository @Inject constructor(localDataSource: LocalDataSource) {
    val local = localDataSource
}