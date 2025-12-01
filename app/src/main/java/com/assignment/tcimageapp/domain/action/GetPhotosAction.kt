package com.assignment.tcimageapp.domain.action

import com.assignment.tcimageapp.core.internet.NetworkState
import com.assignment.tcimageapp.core.internet.NoInternetException
import com.assignment.tcimageapp.domain.repository.PhotosRepository
import com.assignment.tcimageapp.data.remote.dto.PhotoDto
import com.assignment.tcimageapp.di.annotations.LocalQualifier
import com.assignment.tcimageapp.di.annotations.RemoteQualifier
import jakarta.inject.Inject

/**
 * Retrieves the list of photos either from API or datastore,
 * @param offlineEnabled Whether offline-sync mode is enabled by the user.
 *
 * @return Result containing a list of photos, or an error if nothing is available.
 */
class GetPhotosAction @Inject constructor(
    private val networkMonitor: NetworkState,
    @RemoteQualifier private val remotePhotoRepository: PhotosRepository,
    @LocalQualifier private val localPhotoRepository: PhotosRepository
) {
    suspend operator fun invoke(offlineEnabled: Boolean): Result<List<PhotoDto>> {

        val isOnline = networkMonitor.isOnline()

        if (!offlineEnabled) {
            if (!isOnline) {
                // device offline and online mode enabled
                return Result.failure(NoInternetException())
            } else {
                // device online and online mode enabled
                return remotePhotoRepository.fetchPhotos()
            }
        }

        if (isOnline) {
            // device online and offline mode enabled
            val remotePhotos = remotePhotoRepository.fetchPhotos()
            remotePhotos
                .onSuccess { photos ->
                    localPhotoRepository.savePhotos(photos)
                }
                .onFailure { e ->
                     val offlinePhotos = localPhotoRepository.fetchPhotos()
                    offlinePhotos
                        .onSuccess { photos ->
                            return offlinePhotos
                        }
                }
            return remotePhotos
        } else {
            // device offline and offline mode enabled
            val offlinePhotos = localPhotoRepository.fetchPhotos()
            offlinePhotos
                .onSuccess { photos ->
                    return offlinePhotos
                }
            return Result.failure(NoInternetException())
        }
    }
}