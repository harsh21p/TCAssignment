package com.assignment.tcimageapp.domain.repository

import com.assignment.tcimageapp.data.remote.dto.PhotoDto

/**
 * Repository interface that defines all photo related data operations.
 *
 * making the code testable
 */
interface PhotosRepository {

    suspend fun fetchPhotos(): Result<List<PhotoDto>>

    suspend fun deletePhotos()

    suspend fun  savePhotos(photos: List<PhotoDto>): Result<Boolean>

}