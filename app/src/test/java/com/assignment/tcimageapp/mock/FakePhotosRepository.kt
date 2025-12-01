package com.assignment.tcimageapp.mock

import com.assignment.tcimageapp.data.remote.dto.PhotoDto
import com.assignment.tcimageapp.domain.repository.PhotosRepository

/**
 * Simple mock repository for testing.
 */
class FakePhotosRepository(
    private val photos: List<PhotoDto>
) : PhotosRepository {

    override suspend fun fetchPhotos(): Result<List<PhotoDto>> {
        return Result.success(photos)
    }

    override suspend fun deletePhotos() {
        TODO("Not yet implemented")
    }

    override suspend fun savePhotos(photos: List<PhotoDto>): Result<Boolean> {
        TODO("Not yet implemented")
    }
}