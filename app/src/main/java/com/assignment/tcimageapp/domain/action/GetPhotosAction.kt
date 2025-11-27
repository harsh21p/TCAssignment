package com.assignment.tcimageapp.domain.action

import com.assignment.tcimageapp.domain.repository.PhotosRepository
import com.assignment.tcimageapp.data.remote.dto.PhotoDto
import jakarta.inject.Inject

/**
 * Retrieves the list of photos either from API or datastore,
 * @param offlineEnabled Whether offline-sync mode is enabled by the user.
 *
 * @return Result containing a list of photos, or an error if nothing is available.
 */
class GetPhotosAction @Inject constructor(
    private val repository: PhotosRepository
) {
    suspend operator fun invoke(offlineEnabled: Boolean): Result<List<PhotoDto>> {
        return repository.fetchPhotos(offlineEnabled)
    }
}