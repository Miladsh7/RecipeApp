package com.msh.recipapp.di

import androidx.fragment.app.Fragment
import com.msh.recipapp.ui.bookMark.adapter.VideoBookMarkAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {
    @Provides
    fun provideAdapterExplore(fragment: Fragment): VideoBookMarkAdapter {
        return VideoBookMarkAdapter(fragment.lifecycle)
    }
}
