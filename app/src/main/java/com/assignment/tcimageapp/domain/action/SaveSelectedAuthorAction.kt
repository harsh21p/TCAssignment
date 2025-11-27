package com.assignment.tcimageapp.domain.action

import com.assignment.tcimageapp.domain.repository.PhotosRepository
import jakarta.inject.Inject

class SaveSelectedAuthorAction @Inject constructor(
    private val repository: PhotosRepository
) {
    suspend operator fun invoke(author: String?) {
        repository.saveSelectedAuthor(author)
    }
}