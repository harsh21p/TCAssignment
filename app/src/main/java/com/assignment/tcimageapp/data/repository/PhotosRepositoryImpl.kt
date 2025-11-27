package com.assignment.tcimageapp.data.repository

import com.assignment.tcimageapp.data.local.AuthorFilterDataSource
import com.assignment.tcimageapp.data.local.PhotosCacheDataSource
import com.assignment.tcimageapp.data.remote.api.PicsumApiService
import com.assignment.tcimageapp.data.remote.dto.PhotoDto
import com.assignment.tcimageapp.domain.repository.PhotosRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlin.Result
import com.assignment.tcimageapp.core.internet.NetworkState
import com.assignment.tcimageapp.core.internet.NoInternetException

@Singleton
class PhotosRepositoryImpl @Inject constructor(
    private val apiService: PicsumApiService,
    private val authorFilterDataSource: AuthorFilterDataSource,
    private val photosCacheDataSource: PhotosCacheDataSource,
    private val networkMonitor: NetworkState
) : PhotosRepository {

    override suspend fun fetchPhotos(): Result<List<PhotoDto>> {
        if (!networkMonitor.isOnline()) {
            val cachedPhotos = photosCacheDataSource.getCachedPhotos()
            return if (cachedPhotos.isNotEmpty()) {
                Result.success(cachedPhotos)
            } else {
                Result.failure(NoInternetException())
            }
        }

        return try {
            val remotePhotos = apiService.getPhotos()

            photosCacheDataSource.savePhotos(remotePhotos)

            Result.success(remotePhotos)
        } catch (e: Exception) {
            val cachedPhotos = photosCacheDataSource.getCachedPhotos()
            if (cachedPhotos.isNotEmpty()) {
                Result.success(cachedPhotos)
            } else {
                Result.failure(e)
            }
        }
    }

    override fun observeSelectedAuthor(): Flow<String?> {
        return authorFilterDataSource.selectedAuthorFlow
    }

    override suspend fun saveSelectedAuthor(author: String?) {
        authorFilterDataSource.setSelectedAuthor(author)
    }
}