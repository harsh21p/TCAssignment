package com.assignment.tcimageapp.data.local

import javax.inject.Singleton
import javax.inject.Inject

import androidx.datastore.core.DataStore
import com.assignment.tcimageapp.data.remote.dto.CachedPhotos
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
    private val dataStore: DataStore<CachedPhotos>,
) {
    suspend fun savePhotos(photos: List<PhotoDto>) {
        dataStore.updateData { current ->
            current.copy(photos = photos)
        }
    }
    suspend fun getCachedPhotos(): List<PhotoDto> {
        return dataStore.data.first().photos
    }

    suspend fun clearPhotos() {
        dataStore.updateData { CachedPhotos() }
    }
}