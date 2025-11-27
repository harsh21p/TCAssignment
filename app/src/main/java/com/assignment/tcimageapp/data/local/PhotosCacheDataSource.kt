package com.assignment.tcimageapp.data.local

import javax.inject.Singleton
import javax.inject.Inject

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import androidx.datastore.preferences.core.edit
import com.assignment.tcimageapp.data.remote.dto.PhotoDto
import kotlinx.coroutines.flow.first

/**
 * Handles saving and retrieving cached photos using DataStore.
 *
 * The caching format string stored in a Preferences DataStore.
 *
 */
@Singleton
class PhotosCacheDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    moshi: Moshi
) {

    private object Keys {
        val CACHED_PHOTOS_JSON = stringPreferencesKey("cached_photos_json")
    }

    private val type = Types.newParameterizedType(List::class.java, PhotoDto::class.java)
    private val adapter = moshi.adapter<List<PhotoDto>>(type)

    suspend fun savePhotos(photos: List<PhotoDto>) {
        val json = adapter.toJson(photos)
        dataStore.edit { prefs ->
            prefs[Keys.CACHED_PHOTOS_JSON] = json
        }
    }

    suspend fun getCachedPhotos(): List<PhotoDto> {
        val prefs = dataStore.data.first()
        val json = prefs[Keys.CACHED_PHOTOS_JSON] ?: return emptyList()
        return runCatching {
            adapter.fromJson(json) ?: emptyList()
        }.getOrElse { emptyList() }
    }

    suspend fun clearPhotos() {
        dataStore.edit { prefs ->
            prefs.remove(Keys.CACHED_PHOTOS_JSON)
        }
    }
}