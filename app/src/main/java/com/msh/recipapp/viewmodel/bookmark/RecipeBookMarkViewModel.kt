package com.msh.recipapp.viewmodel.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msh.recipapp.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeBookMarkViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {
    val readRecipeBookMarkData = repository.local.loadRecipeBookMark().asLiveData()
}