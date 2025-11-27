package com.assignment.tcimageapp.presentation.feature

import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.assignment.tcimageapp.core.PhotosSortOption
import com.assignment.tcimageapp.domain.action.GetOfflineEnabledAction
import com.assignment.tcimageapp.domain.action.GetPhotosAction
import com.assignment.tcimageapp.domain.action.GetSelectedAuthorAction
import com.assignment.tcimageapp.domain.action.SaveOfflineEnabledAction
import com.assignment.tcimageapp.domain.action.SaveSelectedAuthorAction
import kotlinx.coroutines.flow.first
import com.assignment.tcimageapp.domain.action.ClearPhotosCacheAction

import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import android.util.Log


@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val getSelectedAuthorAction: GetSelectedAuthorAction,
    private val saveSelectedAuthorUseCase: SaveSelectedAuthorAction,
    private val clearPhotosCacheAction: ClearPhotosCacheAction,
    private val getOfflineEnabledAction: GetOfflineEnabledAction,
    private val saveOfflineEnabledAction: SaveOfflineEnabledAction,
    private val getPhotosAction: GetPhotosAction
) : ViewModel() {

    private val myPhotosMain = MutableStateFlow(PhotosUiState())
    val myPhotos: StateFlow<PhotosUiState> = myPhotosMain.asStateFlow()


    init {
        observeSelectedAuthor()
        viewModelScope.launch {
            val offlineEnabled = getOfflineEnabledAction().first()
            myPhotosMain.update {
                it.copy(isOfflineEnabled = offlineEnabled)
            }
            loadPhotos()
        }
    }

    private fun observeSelectedAuthor() {
        viewModelScope.launch {
            getSelectedAuthorAction().collect { savedAuthor ->
                myPhotosMain.update { it.copy(selectedAuthor = savedAuthor) }
                applyFilterAndSort()
            }
        }
    }

    fun loadPhotos() {
        viewModelScope.launch {
            myPhotosMain.update { it.copy(isLoading = true, errorMessage = null) }

            val offlineEnabled = myPhotosMain.value.isOfflineEnabled
            Log.e("VM",offlineEnabled.toString())
            val result = getPhotosAction(offlineEnabled)

            result
                .onSuccess { photos ->
                    val authors = photos.map { it.author }.distinct().sorted()
                    Log.e("VM","SUCCESS")
                    myPhotosMain.update {
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
                    Log.e("VM","FAIL")
                    myPhotosMain.update {
                        it.copy(
                            isLoading = false,
                            allPhotos = emptyList(),
                            authors = emptyList(),
                            filteredPhotos = emptyList(),
                            errorMessage = throwable.message
                        )
                    }
                }
        }
    }

    fun onAuthorSelected(author: String?) {
        viewModelScope.launch {
            myPhotosMain.update { it.copy(selectedAuthor = author) }
            applyFilterAndSort()
            saveSelectedAuthorUseCase(author)
        }
    }

    // Not using after sort option is added but it was the initial implementation
    private fun applyFilter() {
        val state = myPhotosMain.value
        val selectedAuthor = state.selectedAuthor
        val filtered = if (selectedAuthor.isNullOrEmpty()) {
            state.allPhotos
        } else {
            state.allPhotos.filter { it.author == selectedAuthor }
        }
        myPhotosMain.update { it.copy(filteredPhotos = filtered) }
    }

    fun retry() {
        loadPhotos()
    }

    private fun applyFilterAndSort() {
        val state = myPhotosMain.value

        val filtered = if (state.selectedAuthor.isNullOrEmpty()) {
            state.allPhotos
        } else {
            state.allPhotos.filter { it.author == state.selectedAuthor }
        }

        val sorted = when (state.sortOption) {
            PhotosSortOption.DEFAULT -> filtered

            PhotosSortOption.AUTHOR ->
                filtered.sortedByDescending { it.author.lowercase() }

            PhotosSortOption.HEIGHT ->
                filtered.sortedByDescending { it.height }
        }

        myPhotosMain.update { it.copy(filteredPhotos = sorted) }
    }

    fun onSortOptionSelected(option: PhotosSortOption) {
        myPhotosMain.update { it.copy(sortOption = option) }
        applyFilterAndSort()
    }

    fun onOfflineSyncToggled(enabled: Boolean) {
        viewModelScope.launch {
            myPhotosMain.update { it.copy(isOfflineEnabled = enabled) }

            saveOfflineEnabledAction(enabled)

            if (!enabled) {
                clearPhotosCacheAction()
            }
            loadPhotos()
        }
    }
}