package com.assignment.tcimageapp.data.repository

import com.assignment.tcimageapp.data.local.PhotosCacheDataSource
import com.assignment.tcimageapp.data.remote.dto.PhotoDto
import com.assignment.tcimageapp.domain.repository.PhotosRepository
import jakarta.inject.Singleton
import javax.inject.Inject

@Singleton
class LocalPhotoRepository @Inject constructor(
    private val photosCacheDataSource: PhotosCacheDataSource,
): PhotosRepository {
    override suspend fun fetchPhotos(): Result<List<PhotoDto>> {
        return try {
            Result.success(photosCacheDataSource.getCachedPhotos())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deletePhotos() {
        photosCacheDataSource.clearPhotos()
    }

    override suspend fun savePhotos(photos: List<PhotoDto>): Result<Boolean> {
        try {
            photosCacheDataSource.savePhotos(photos)
            return Result.success(true)
        } catch (e: Exception){
            return Result.failure(e)
        }
    }
}