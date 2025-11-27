package com.assignment.tcimageapp.domain.action

import com.assignment.tcimageapp.domain.repository.PhotosRepository
import com.assignment.tcimageapp.data.remote.dto.PhotoDto
import jakarta.inject.Inject


class GetPhotosAction @Inject constructor(
    private val repository: PhotosRepository
) {
    suspend operator fun invoke(offlineEnabled: Boolean): Result<List<PhotoDto>> {
        return repository.fetchPhotos(offlineEnabled)
    }
}