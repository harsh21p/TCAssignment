package com.assignment.tcimageapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthorRepository {
    fun observeSelectedAuthor(): Flow<String?>
    suspend fun saveSelectedAuthor(author: String?)
}