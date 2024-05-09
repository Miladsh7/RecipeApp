package com.msh.recipapp.viewmodel.more

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.msh.recipapp.data.database.entity.more.MoreVideoEntity
import com.msh.recipapp.data.repository.HomeRepository
import com.msh.recipapp.models.home.ResponseVideo
import com.msh.recipapp.utils.API_KEY
import com.msh.recipapp.utils.CUISINE
import com.msh.recipapp.utils.EUROPEAN
import com.msh.recipapp.utils.FULL_COUNT
import com.msh.recipapp.utils.MY_API_KEY
import com.msh.recipapp.utils.NUMBER
import com.msh.recipapp.utils.NetworkRequest
import com.msh.recipapp.utils.NetworkResponse
import com.msh.recipapp.utils.SOUP
import com.msh.recipapp.utils.TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingVideoViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    //--video--//
    //Queries
    fun trendingVideoQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[API_KEY] = MY_API_KEY
        queries[TYPE] = SOUP
        queries[CUISINE] = EUROPEAN
        queries[NUMBER] = FULL_COUNT.toString()
        return queries
    }

    val trendingVideoData = MutableLiveData<NetworkRequest<ResponseVideo>>()
    //Api
    fun callTrendVideoApi(queries: Map<String, String>) = viewModelScope.launch {
        trendingVideoData.value = NetworkRequest.Loading()
        //trendingVideoData.postValue(NetworkRequest.Loading())
        val response = repository.remote.getVideoTrending(queries)
        trendingVideoData.value = NetworkResponse(response).generalNetworkResponse()
        //trendingVideoData.postValue(NetworkResponse(response).generalNetworkResponse())

        val cache = trendingVideoData.value?.data
        if (cache != null)
            offlineVideo(cache)
    }

    //Local
    private fun saveVideo(entity: MoreVideoEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.moreSaveVideos(entity)
    }

    val readMoreVideoFromDb = repository.local.moreLoadVideos().asLiveData()

    private fun offlineVideo(response: ResponseVideo) {
        val entity = MoreVideoEntity(0, response)
        saveVideo(entity)
    }
}