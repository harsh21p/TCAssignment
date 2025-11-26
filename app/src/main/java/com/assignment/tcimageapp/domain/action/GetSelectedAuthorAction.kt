package com.assignment.tcimageapp.domain.action

import com.assignment.tcimageapp.domain.repository.PhotosRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetSelectedAuthorAction @Inject constructor(
    private val repository: PhotosRepository
) {
    operator fun invoke(): Flow<String?> {
        return repository.observeSelectedAuthor()
    }
}