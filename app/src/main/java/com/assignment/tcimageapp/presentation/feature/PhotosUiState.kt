package com.assignment.tcimageapp.presentation.feature

import com.assignment.tcimageapp.data.remote.dto.PhotoDto


data class PhotosUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val allPhotos: List<PhotoDto> = emptyList(),
    val filteredPhotos: List<PhotoDto> = emptyList(),
    val authors: List<String> = emptyList(),
    val selectedAuthor: String? = null
)