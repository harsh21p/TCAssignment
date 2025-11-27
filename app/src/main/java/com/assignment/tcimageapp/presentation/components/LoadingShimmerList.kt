package com.assignment.tcimageapp.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.Modifier


/**
 * Shows a full screen shimmer list while loading photos.
 *
 * Mimics the exact structure of the final grid:
 *  - Two columns
 *  - Randomized heights for natural feel
 */
@Composable
fun LoadingShimmerList(
    modifier: Modifier = Modifier
) {
    val skeletonItems = List(12) { it } // 12 shimmer items

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize()
    ) {
        items(skeletonItems) {
            PhotosGridSkeletonItem()
        }
    }
}

