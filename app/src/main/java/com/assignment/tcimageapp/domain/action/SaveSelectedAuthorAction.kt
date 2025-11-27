package com.assignment.tcimageapp.domain.action

import com.assignment.tcimageapp.domain.repository.PhotosRepository
import jakarta.inject.Inject

/**
 * Returns a Flow that emits the currently selected author name.
 *
 * This value is stored using DataStore so it persists across app restarts.
 *
 * @return Flow emitting the selected author or null if no filter is applied.
 */
class SaveSelectedAuthorAction @Inject constructor(
    private val repository: PhotosRepository
) {
    suspend operator fun invoke(author: String?) {
        repository.saveSelectedAuthor(author)
    }
}