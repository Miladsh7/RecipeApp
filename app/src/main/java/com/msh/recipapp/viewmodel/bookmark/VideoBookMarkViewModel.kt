package com.msh.recipapp.viewmodel.bookmark

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.msh.recipapp.data.database.entity.bookmark.VideoBookmarkEntity
import com.msh.recipapp.data.repository.VideoBookMarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoBookMarkViewModel @Inject constructor(
    val repository: VideoBookMarkRepository
) : ViewModel() {

    val readVideoBookMarkData = repository.local.loadVideoBookMark().asLiveData()

    fun saveVideoBookMark(entity: VideoBookmarkEntity) = viewModelScope.launch {
        repository.local.saveVideoBookMark(entity)
    }

    fun deleteVideoBookMark(entity: VideoBookmarkEntity) = viewModelScope.launch {
        repository.local.deleteVideoBookMark(entity)
    }

    val existsVideoBookMarkData = MutableLiveData<Boolean>()
    fun existsVideoBookMark(id: String) = viewModelScope.launch {
        repository.local.existVideoBookMark(id).collect {
            existsVideoBookMarkData.postValue(it)
        }
    }

}