package com.assignment.tcimageapp.presentation.feature

import com.assignment.tcimageapp.core.PhotosSortOption
import com.assignment.tcimageapp.data.remote.dto.PhotoDto

/**
 * Immutable UI state used by PhotosScreen.
 *
 * Stores:
 *  - full photo list
 *  - filtered + sorted photos
 *  - loading/error states
 *  - selected author
 *  - available authors
 *  - current sort option
 *  - offline sync toggle
 *
 * All UI rendering is controlled by this state.
 */
data class PhotosUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val allPhotos: List<PhotoDto> = emptyList(),
    val filteredPhotos: List<PhotoDto> = emptyList(),
    val authors: List<String> = emptyList(),
    val selectedAuthor: String? = null,
    val isOfflineEnabled: Boolean = false,
    val sortOption: PhotosSortOption = PhotosSortOption.DEFAULT
)