package com.assignment.tcimageapp.domain.action

import com.assignment.tcimageapp.domain.repository.PhotosRepository
import kotlinx.coroutines.flow.Flow

import jakarta.inject.Inject

class GetSelectedAuthorAction @Inject constructor(
    private val repository: PhotosRepository
) {
    operator fun invoke(): Flow<String?> {
        return repository.observeSelectedAuthor()
    }
}