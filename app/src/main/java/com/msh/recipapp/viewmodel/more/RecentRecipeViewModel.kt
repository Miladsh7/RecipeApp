package com.msh.recipapp.viewmodel.more

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.msh.recipapp.data.database.entity.more.MoreRecentEntity
import com.msh.recipapp.data.repository.HomeRepository
import com.msh.recipapp.models.home.ResponseRecent
import com.msh.recipapp.utils.API_KEY
import com.msh.recipapp.utils.FULL_COUNT
import com.msh.recipapp.utils.MAIN_COURSE
import com.msh.recipapp.utils.MY_API_KEY
import com.msh.recipapp.utils.NUMBER
import com.msh.recipapp.utils.NetworkRequest
import com.msh.recipapp.utils.NetworkResponse
import com.msh.recipapp.utils.TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentRecipeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    //--Recent--//
    //Queries
    fun recentRecipeQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[API_KEY] = MY_API_KEY
        queries[TYPE] = MAIN_COURSE
        queries[NUMBER] = FULL_COUNT.toString()
        return queries
    }

    val moreRecentData = MutableLiveData<NetworkRequest<ResponseRecent>>()
    //Api
    fun callRecentApi(queries: Map<String, String>) = viewModelScope.launch {
        moreRecentData.value = NetworkRequest.Loading()
        val response = repository.remote.getRecent(queries)
        moreRecentData.value = NetworkResponse(response).generalNetworkResponse()

        //cache
        val cache = moreRecentData.value?.data
        if (cache != null)
            offlineRecent(cache)
    }

    //Local
    private fun saveRecent(entity: MoreRecentEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.saveMoreRecent(entity)
    }

    val readMoreRecentFromDb = repository.local.moreLoadRecent().asLiveData()

    private fun offlineRecent(response: ResponseRecent) {
        val entity = MoreRecentEntity(0, response)
        saveRecent(entity)
    }
}