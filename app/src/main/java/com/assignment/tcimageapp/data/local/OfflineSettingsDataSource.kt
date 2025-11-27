package com.assignment.tcimageapp.data.local

import javax.inject.Singleton
import javax.inject.Inject
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class OfflineSettingsDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private object Keys {
        val OFFLINE_ENABLED = booleanPreferencesKey("offline_enabled")
    }

    val offlineEnabledFlow: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[Keys.OFFLINE_ENABLED] ?: false   // default = false
    }

    suspend fun setOfflineEnabled(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[Keys.OFFLINE_ENABLED] = enabled
        }
    }
}