package com.example.roomapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class DataStoreManager(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_pref")

    suspend fun saveToDataStore(timestamp: Long) {
        context.dataStore.edit {
            it[PreferencesKeys.LAST_SYNC] = timestamp
        }
    }
    fun getFromDataStore() : Long = runBlocking {
        context.dataStore.data.map {
            it[PreferencesKeys.LAST_SYNC] ?: 0L
        }.first()
    }
}