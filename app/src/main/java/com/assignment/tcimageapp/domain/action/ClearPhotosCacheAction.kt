package com.assignment.tcimageapp.domain.action

import com.assignment.tcimageapp.di.annotations.LocalQualifier
import com.assignment.tcimageapp.domain.repository.PhotosRepository
import javax.inject.Inject

/**
 * Clears all locally stored photos.
 *
 */
class ClearPhotosCacheAction @Inject constructor(
    @LocalQualifier private val repository: PhotosRepository
) {
    suspend operator fun invoke() {
        repository.deletePhotos()
    }
}