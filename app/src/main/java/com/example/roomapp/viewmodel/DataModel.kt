package com.example.roomapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomapp.DataStoreManager
import com.example.roomapp.data.Exclusions
import com.example.roomapp.data.Facilities
import com.example.roomapp.database.AppDatabase
import com.example.roomapp.repository.Repository

class DataModel(context: Application, dataStoreManager: DataStoreManager): AndroidViewModel(context){

    val dataStoreManager = dataStoreManager
    private var facilitiesList = MutableLiveData<List<Facilities>>()
    val liveFacilitiesList: LiveData<List<Facilities>> get() = facilitiesList
    private var exclusionsList = MutableLiveData<List<List<Exclusions>>>()
    val liveExclusionsList: LiveData<List<List<Exclusions>>> get() = exclusionsList
    val roomDao = AppDatabase.getDatabase(context).appDao()

    private val repository = Repository(roomDao)

    class Factory(val application: Application, val dataStoreManager: DataStoreManager) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DataModel(application, dataStoreManager) as T
        }
    }

     fun getListData(){
        val now = System.currentTimeMillis()
        val last = dataStoreManager.getFromDataStore()
        var syncRequired = false
        val diffMillis: Long = now - last
        if (diffMillis >= 3600000  * 24) {
            syncRequired = true
        }
        repository.getData(facilitiesList, exclusionsList, syncRequired)
    }
}