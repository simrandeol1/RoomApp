package com.example.roomapp

import androidx.datastore.preferences.core.longPreferencesKey

class PreferencesKeys {
    companion object {
        val LAST_SYNC = longPreferencesKey("last_sync")
    }
}