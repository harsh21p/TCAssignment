package com.assignment.tcimageapp.presentation.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.assignment.tcimageapp.data.remote.dto.PhotoDto
import com.assignment.tcimageapp.presentation.feature.PhotosViewModel

/**
 * Main Screen UI
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotosScreen(
    viewModel: PhotosViewModel = hiltViewModel()
) {
    val uiState by viewModel.myPhotos.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        val error = uiState.errorMessage
        if (error != null && uiState.filteredPhotos.isNotEmpty()) {
            snackBarHostState.showSnackbar(error)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Image Viewer",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { viewModel.retry() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            PhotosSortAndSyncBar(
                selectedSort = uiState.sortOption,
                onSortSelected = viewModel::onSortOptionSelected,
                isOfflineEnabled = uiState.isOfflineEnabled,
                onOfflineToggle = viewModel::onOfflineSyncToggled
            )
            FilterDropdown(
                authors = uiState.authors,
                selectedAuthor = uiState.selectedAuthor,
                onAuthorSelected = viewModel::onAuthorSelected
            )

            when {
                uiState.isLoading -> {

                    LoadingShimmerList(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp)
                    )
                }

                uiState.errorMessage != null && uiState.filteredPhotos.isEmpty() -> {
                    ErrorView(
                        message = uiState.errorMessage ?: "Unknown error",
                        onRetry = { viewModel.retry() }
                    )
                }

                else -> {
                    PhotosGrid(
                        photos = uiState.filteredPhotos,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun PhotosGrid(
    photos: List<PhotoDto>,
    modifier: Modifier = Modifier
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 8.dp
    ) {
        items(photos) { photo ->
            PhotosGridItem(photo)
        }
    }
}
