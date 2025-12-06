package com.assignment.tcimageapp.mock

import com.assignment.tcimageapp.domain.repository.AuthorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Simple mock repository for testing.
 */
class FakeAuthorRepository(): AuthorRepository {
    private val authorMain = MutableStateFlow<String?>(null)

    override fun observeSelectedAuthor(): Flow<String?> {
        return authorMain
    }

    override suspend fun saveSelectedAuthor(author: String?) {
        authorMain.value = author
    }

}