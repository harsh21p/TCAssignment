package com.assignment.tcimageapp.domain.action

import com.assignment.tcimageapp.domain.repository.AuthorRepository
import kotlinx.coroutines.flow.Flow

import jakarta.inject.Inject

/**
 * Saves the currently selected author filter into DataStore.
 *
 * @param author Selected author name or null to clear the filter.
 */
class GetSelectedAuthorAction @Inject constructor(
     private val repository: AuthorRepository
) {
    operator fun invoke(): Flow<String?> {
        return repository.observeSelectedAuthor()
    }
}