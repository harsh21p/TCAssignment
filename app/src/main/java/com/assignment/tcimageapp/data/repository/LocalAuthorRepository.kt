package com.assignment.tcimageapp.data.repository

import com.assignment.tcimageapp.data.local.AuthorFilterDataSource
import com.assignment.tcimageapp.domain.repository.AuthorRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class LocalAuthorRepository @Inject constructor(
    private val authorFilterDataSource: AuthorFilterDataSource
) : AuthorRepository {
    override fun observeSelectedAuthor(): Flow<String?> {
        return authorFilterDataSource.selectedAuthorFlow
    }
    override suspend fun saveSelectedAuthor(author: String?) {
        authorFilterDataSource.setSelectedAuthor(author)
    }
}