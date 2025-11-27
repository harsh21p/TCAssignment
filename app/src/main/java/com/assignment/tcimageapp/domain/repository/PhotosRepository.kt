package com.assignment.tcimageapp.domain.repository

import com.assignment.tcimageapp.data.remote.dto.PhotoDto
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {

    suspend fun fetchPhotos(offlineEnabled: Boolean): Result<List<PhotoDto>>

    fun observeSelectedAuthor(): Flow<String?>

    suspend fun saveSelectedAuthor(author: String?)

    suspend fun clearPhotosCache()
}