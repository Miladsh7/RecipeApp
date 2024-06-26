package com.msh.recipapp.di

import android.content.Context
import androidx.room.Room
import com.msh.recipapp.data.database.RecipeAppDatabase
import com.msh.recipapp.utils.TABLE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context)= Room.databaseBuilder(
       context, RecipeAppDatabase::class.java, TABLE_NAME
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(database: RecipeAppDatabase) = database.dao()

}