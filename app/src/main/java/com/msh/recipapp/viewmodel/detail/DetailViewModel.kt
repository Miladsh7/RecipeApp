package com.msh.recipapp.viewmodel.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.msh.recipapp.data.database.entity.bookmark.RecipeBookMarkEntity
import com.msh.recipapp.data.database.entity.detail.DetailEntity
import com.msh.recipapp.data.repository.HomeRepository
import com.msh.recipapp.models.detail.ResponseDetail
import com.msh.recipapp.utils.NetworkRequest
import com.msh.recipapp.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    val detailData = MutableLiveData<NetworkRequest<ResponseDetail>>()
    //Api
    fun callDetailApi(id: Int, apiKey: String) {
        viewModelScope.launch {
            detailData.value = NetworkRequest.Loading()
            val response = repository.remote.getDetail(id, apiKey)
            detailData.value = NetworkResponse(response).generalNetworkResponse()

            //cache
            val cache = detailData.value?.data
            if (cache != null)
                cacheDetail(cache.id!!, cache)
        }
    }

    //Local
    private fun saveDetail(entity: DetailEntity) = viewModelScope.launch {
        repository.local.saveDetail(entity)
    }

    fun readDetailFromDb(id: Int) = repository.local.loadDetail(id).asLiveData()

    val existsDetailData = MutableLiveData<Boolean>()
    fun existsDetail(id: Int) = viewModelScope.launch {
        repository.local.existsDetail(id).collect { existsDetailData.postValue(it) }
    }

    private fun cacheDetail(id: Int, response: ResponseDetail) {
        val entity = DetailEntity(id, response)
        saveDetail(entity)
    }

    //BookMark
    fun saveRecipeBookMark(entity: RecipeBookMarkEntity) = viewModelScope.launch {
        repository.local.saveRecipeBookMark(entity)
    }

    fun deleteRecipeBookMark(entity: RecipeBookMarkEntity) = viewModelScope.launch {
        repository.local.deleteRecipeBookMark(entity)
    }

    val existsRecipeBookMarkData = MutableLiveData<Boolean>()
    fun existsRecipeBookMark(id: Int) = viewModelScope.launch {
        repository.local.existRecipeBookMark(id).collect {
            existsRecipeBookMarkData.postValue(it)
        }
    }
}

