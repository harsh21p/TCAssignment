package com.assignment.tcimageapp.data.repository

import com.assignment.tcimageapp.data.local.AuthorFilterDataSource
import com.assignment.tcimageapp.data.remote.api.PicsumApiService
import com.assignment.tcimageapp.data.remote.dto.PhotoDto
import com.assignment.tcimageapp.domain.repository.PhotosRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class PhotosRepositoryImpl @Inject constructor(
    private val apiService: PicsumApiService,
    private val authorFilterDataSource: AuthorFilterDataSource
) : PhotosRepository {

    override suspend fun fetchPhotos(): Result<List<PhotoDto>> {
        return try {
            val photos = apiService.getPhotos() // List<PhotoDto>
            Result.success(photos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observeSelectedAuthor(): Flow<String?> {
        return authorFilterDataSource.selectedAuthorFlow
    }

    override suspend fun saveSelectedAuthor(author: String?) {
        authorFilterDataSource.setSelectedAuthor(author)
    }
}