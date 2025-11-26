package com.assignment.tcimageapp.domain.action

import com.assignment.tcimageapp.data.remote.dto.PhotoDto
import com.assignment.tcimageapp.domain.repository.PhotosRepository
import jakarta.inject.Inject

class GetPhotosAction @Inject constructor(
    private val repository: PhotosRepository
) {
    suspend operator fun invoke(): Result<List<PhotoDto>> {
        return repository.fetchPhotos()
    }
}