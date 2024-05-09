package com.msh.recipapp.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.msh.recipapp.data.Type
import com.msh.recipapp.data.database.entity.home.PopularEntity
import com.msh.recipapp.data.database.entity.home.RecentEntity
import com.msh.recipapp.data.database.entity.home.VideoEntity
import com.msh.recipapp.data.repository.HomeRepository
import com.msh.recipapp.models.home.ResponsePopular
import com.msh.recipapp.models.home.ResponseRecent
import com.msh.recipapp.models.home.ResponseVideo
import com.msh.recipapp.utils.ADD_RECIPE_INFORMATION
import com.msh.recipapp.utils.API_KEY
import com.msh.recipapp.utils.CUISINE
import com.msh.recipapp.utils.EUROPEAN
import com.msh.recipapp.utils.LIMITED_COUNT
import com.msh.recipapp.utils.MAIN_COURSE
import com.msh.recipapp.utils.MY_API_KEY
import com.msh.recipapp.utils.NUMBER
import com.msh.recipapp.utils.NetworkRequest
import com.msh.recipapp.utils.NetworkResponse
import com.msh.recipapp.utils.SOUP
import com.msh.recipapp.utils.TRUE
import com.msh.recipapp.utils.TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    //--Video--//
    //Queries
    fun trendingVideoQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[API_KEY] = MY_API_KEY
        queries[TYPE] = SOUP
        queries[CUISINE] = EUROPEAN
        queries[NUMBER] = LIMITED_COUNT.toString()
        return queries
    }

    val trendingVideoData = MutableLiveData<NetworkRequest<ResponseVideo>>()

    //Api
    fun callTrendVideoApi(queries: Map<String, String>) = viewModelScope.launch {
        trendingVideoData.value = NetworkRequest.Loading()
        val response = repository.remote.getVideoTrending(queries)
        trendingVideoData.value = NetworkResponse(response).generalNetworkResponse()

        val cache = trendingVideoData.value?.data
        if (cache != null)
            offlineVideo(cache)
    }

    //Local
    private fun saveVideo(entity: VideoEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.saveVideos(entity)
    }

    val readVideoFromDb = repository.local.loadVideos().asLiveData()


    private fun offlineVideo(response: ResponseVideo) {
        val entity = VideoEntity(0, response)
        saveVideo(entity)
    }

    //--Popular--//
    //Queries
    fun popularQueries(mealType: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[API_KEY] = MY_API_KEY
        queries[TYPE] = mealType
        queries[NUMBER] = LIMITED_COUNT.toString()
        queries[ADD_RECIPE_INFORMATION] = TRUE
        return queries
    }

    val popularData = MutableLiveData<NetworkRequest<ResponsePopular>>()

    fun callPopularApi(queries: HashMap<String, String>, mealType: String) = viewModelScope.launch {
        popularData.value = NetworkRequest.Loading()
        val response = repository.remote.getPopular(queries)
        popularData.value = NetworkResponse(response).generalNetworkResponse()

        val cache = popularData.value?.data
        if (cache != null)
            offlinePopular(cache, mealType)
    }

    //Local
    fun selectReadPopularDb(id: Int): LiveData<List<PopularEntity>> {
        return repository.local.loadPopular(id).asLiveData()
    }

    private fun savePopular(entity: PopularEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.savePopular(entity)
    }

    private fun offlinePopular(response: ResponsePopular, mealType: String) {

        when (mealType) {
            Type.SALAD.name -> {
                val saladEntity = PopularEntity(0, response)
                savePopular(saladEntity)
            }

            Type.BREAKFAST.name -> {
                val breakFastEntity = PopularEntity(1, response)
                savePopular(breakFastEntity)
            }

            Type.SOUP.name -> {
                val soupEntity = PopularEntity(2, response)
                savePopular(soupEntity)
            }

            Type.SNACK.name -> {
                val snackEntity = PopularEntity(3, response)
                savePopular(snackEntity)
            }

            Type.BREAD.name -> {
                val breadEntity = PopularEntity(4, response)
                savePopular(breadEntity)
            }

            Type.SAUCE.name -> {
                val sauceEntity = PopularEntity(5, response)
                savePopular(sauceEntity)
            }

            Type.DRINK.name -> {
                val drinkEntity = PopularEntity(6, response)
                savePopular(drinkEntity)
            }

            Type.DESSERT.name -> {
                val dessertEntity = PopularEntity(7, response)
                savePopular(dessertEntity)
            }
        }
    }

    //--Recent--//
    //Queries
    fun recentQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[API_KEY] = MY_API_KEY
        queries[TYPE] = MAIN_COURSE
        queries[NUMBER] = LIMITED_COUNT.toString()
        return queries
    }

    val recentData = MutableLiveData<NetworkRequest<ResponseRecent>>()
    fun callRecentApi(queries: Map<String, String>) = viewModelScope.launch {
        recentData.value = NetworkRequest.Loading()
        val response = repository.remote.getRecent(queries)
        recentData.value = NetworkResponse(response).generalNetworkResponse()

        val cache = recentData.value?.data
        if (cache != null)
            offlineRecent(cache)
    }

    //Local
    private fun saveRecent(entity: RecentEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.saveRecent(entity)
    }

    val readRecentFromDb = repository.local.loadRecent().asLiveData()

    private fun offlineRecent(response: ResponseRecent) {
        val entity = RecentEntity(0, response)
        saveRecent(entity)
    }
}