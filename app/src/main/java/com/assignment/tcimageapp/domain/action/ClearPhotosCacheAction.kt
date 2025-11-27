package com.assignment.tcimageapp.domain.action

import com.assignment.tcimageapp.domain.repository.PhotosRepository
import javax.inject.Inject

class ClearPhotosCacheAction @Inject constructor(
    private val repository: PhotosRepository
) {
    suspend operator fun invoke() {
        repository.clearPhotosCache()
    }
}