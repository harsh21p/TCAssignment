package com.assignment.tcimageapp.data.repository

import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlin.Result
import kotlinx.coroutines.flow.Flow

import com.assignment.tcimageapp.data.local.AuthorFilterDataSource
import com.assignment.tcimageapp.data.local.PhotosCacheDataSource
import com.assignment.tcimageapp.data.remote.api.PicsumApiService
import com.assignment.tcimageapp.data.remote.dto.PhotoDto
import com.assignment.tcimageapp.domain.repository.PhotosRepository
import com.assignment.tcimageapp.core.internet.NetworkState
import com.assignment.tcimageapp.core.internet.NoInternetException
import android.util.Log

@Singleton
class PhotosRepositoryImpl @Inject constructor(
    private val apiService: PicsumApiService,
    private val authorFilterDataSource: AuthorFilterDataSource,
    private val photosCacheDataSource: PhotosCacheDataSource,
    private val networkMonitor: NetworkState
) : PhotosRepository {

    override suspend fun fetchPhotos(offlineEnabled: Boolean): Result<List<PhotoDto>> {
        val isOnline = networkMonitor.isOnline()

        if (!offlineEnabled) {
            if (!isOnline) return Result.failure(NoInternetException())

            return try {
                Log.d("API", "CALL 1")
                Result.success(apiService.getPhotos())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        if (isOnline) {
            return try {
                Log.d("API", "CALL 2")
                val remotePhotos = apiService.getPhotos()
                photosCacheDataSource.savePhotos(remotePhotos)
                Result.success(remotePhotos)
            } catch (e: Exception) {
                val cached = photosCacheDataSource.getCachedPhotos()
                if (cached.isNotEmpty()) Result.success(cached)
                else Result.failure(e)
            }
        }

        val cached = photosCacheDataSource.getCachedPhotos()
        return if (cached.isNotEmpty()) {
            Result.success(cached)
        } else {
            Result.failure(NoInternetException())
        }
    }

    override fun observeSelectedAuthor(): Flow<String?> {
        return authorFilterDataSource.selectedAuthorFlow
    }

    override suspend fun saveSelectedAuthor(author: String?) {
        authorFilterDataSource.setSelectedAuthor(author)
    }

    override suspend fun clearPhotosCache() {
        photosCacheDataSource.clearPhotos()
    }

}