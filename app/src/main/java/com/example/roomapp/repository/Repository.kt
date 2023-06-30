package com.example.roomapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.roomapp.data.Exclusions
import com.example.roomapp.data.Facilities
import com.example.roomapp.interfaces.RoomApi
import com.example.roomapp.interfaces.RoomDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Repository class for data
 */
class Repository(private val roomDao: RoomDao){

    private val baseUrl = "https://my-json-server.typicode.com/iranjith4/ad-assignment/"

    private lateinit var roomApi: RoomApi

    init {
        createInstance()
    }

    private fun createInstance() {
        roomApi = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RoomApi::class.java)
    }

    private suspend fun setData(){
        val result = roomApi.getFacilities()
        roomDao.insertItems(result.body())
    }

    fun getData(facilitiesList: MutableLiveData<List<Facilities>>, exclusionsList: MutableLiveData<List<List<Exclusions>>>, isSyncRequired: Boolean){
        GlobalScope.launch {
            if(isSyncRequired)
                setData()
            val data = roomDao.getAllDataSet()
            facilitiesList.postValue(data.facilities)
            exclusionsList.postValue(data.exclusions)
        }
    }
}