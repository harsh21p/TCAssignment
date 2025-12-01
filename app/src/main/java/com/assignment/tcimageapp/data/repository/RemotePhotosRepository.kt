package com.assignment.tcimageapp.data.repository

import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlin.Result

import com.assignment.tcimageapp.data.remote.api.PicsumApiService
import com.assignment.tcimageapp.data.remote.dto.PhotoDto
import com.assignment.tcimageapp.domain.repository.PhotosRepository

/**
 * Concrete implementation of [PhotosRepository].
 *
 * Handles:
 *  - API calls using Retrofit
 *  - Reading/writing datastore photos
 *
 */
@Singleton
class RemotePhotosRepository @Inject constructor(
    private val apiService: PicsumApiService,
) : PhotosRepository {

    override suspend fun fetchPhotos(): Result<List<PhotoDto>> {
        return try {
            Result.success(apiService.getPhotos())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deletePhotos() {
        TODO("Not yet implemented")
    }

    override suspend fun savePhotos(photos: List<PhotoDto>): Result<Boolean> {
        TODO("Not yet implemented")
    }
}

