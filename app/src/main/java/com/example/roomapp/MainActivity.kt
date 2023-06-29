package com.example.roomapp

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.roomapp.adapter.CustomAdapter
import com.example.roomapp.viewmodel.DataModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataStoreManager = DataStoreManager(this)
        val viewModel = ViewModelProvider(this, DataModel.Factory(application,dataStoreManager))[DataModel::class.java]
        val propertySpinner: Spinner = findViewById(R.id.property)
        val roomSpinner: Spinner = findViewById(R.id.rooms)
        val otherSpinner: Spinner = findViewById(R.id.other)

        viewModel.liveFacilitiesList.observe(this){
            val propertyAdapter = CustomAdapter(this, it[0].options)
            propertySpinner.adapter = propertyAdapter
            val roomAdapter = CustomAdapter(this, it[1].options)
            roomSpinner.adapter = roomAdapter
            val otherAdapter = CustomAdapter(this, it[2].options)
            otherSpinner.adapter = otherAdapter
        }
        viewModel.getListData()

    }
}