package com.assignment.tcimageapp.presentation.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.tcimageapp.core.PhotosSortOption
import com.assignment.tcimageapp.domain.action.GetPhotosAction
import com.assignment.tcimageapp.domain.action.GetSelectedAuthorAction
import com.assignment.tcimageapp.domain.action.SaveSelectedAuthorAction
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosAction,
    private val getSelectedAuthorAction: GetSelectedAuthorAction,
    private val saveSelectedAuthorUseCase: SaveSelectedAuthorAction
) : ViewModel() {

    private val _uiState = MutableStateFlow(PhotosUiState())
    val uiState: StateFlow<PhotosUiState> = _uiState.asStateFlow()

    init {
        observeSelectedAuthor()
        loadPhotos()
    }

    private fun observeSelectedAuthor() {
        viewModelScope.launch {
            getSelectedAuthorAction().collect { savedAuthor ->
                _uiState.update { it.copy(selectedAuthor = savedAuthor) }
                applyFilterAndSort()
            }
        }
    }

    fun loadPhotos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = getPhotosUseCase()
            result
                .onSuccess { photos ->
                    val authors = photos.map { it.author }.distinct().sorted()
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            allPhotos = photos,
                            authors = authors,
                            errorMessage = null
                        )
                    }
                    applyFilterAndSort()
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "Unknown error"
                        )
                    }
                }
        }
    }

    fun onAuthorSelected(author: String?) {
        viewModelScope.launch {
            _uiState.update { it.copy(selectedAuthor = author) }
            applyFilterAndSort()
            saveSelectedAuthorUseCase(author)
        }
    }

    // not using after sort option is added
    private fun applyFilter() {
        val state = _uiState.value
        val selectedAuthor = state.selectedAuthor
        val filtered = if (selectedAuthor.isNullOrEmpty()) {
            state.allPhotos
        } else {
            state.allPhotos.filter { it.author == selectedAuthor }
        }
        _uiState.update { it.copy(filteredPhotos = filtered) }
    }

    fun retry() {
        loadPhotos()
    }


    private fun applyFilterAndSort() {
        val state = _uiState.value

        val filtered = if (state.selectedAuthor.isNullOrEmpty()) {
            state.allPhotos
        } else {
            state.allPhotos.filter { it.author == state.selectedAuthor }
        }

        val sorted = when (state.sortOption) {
            PhotosSortOption.DEFAULT -> filtered

            PhotosSortOption.AUTHOR_ASC ->
                filtered.sortedBy { it.author.lowercase() }

            PhotosSortOption.AUTHOR_DESC ->
                filtered.sortedByDescending { it.author.lowercase() }

            PhotosSortOption.WIDTH_ASC ->
                filtered.sortedBy { it.width }

            PhotosSortOption.WIDTH_DESC ->
                filtered.sortedByDescending { it.width }

            PhotosSortOption.HEIGHT_ASC ->
                filtered.sortedBy { it.height }

            PhotosSortOption.HEIGHT_DESC ->
                filtered.sortedByDescending { it.height }
        }

        _uiState.update { it.copy(filteredPhotos = sorted) }
    }

    fun onSortOptionSelected(option: PhotosSortOption) {
        _uiState.update { it.copy(sortOption = option) }
        applyFilterAndSort()
    }
}