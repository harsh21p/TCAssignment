package com.assignment.tcimageapp.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.assignment.tcimageapp.core.PhotosSortOption

/**
 * Toggle Button and sort filters
 *
 */
@Composable
fun PhotosSortAndSyncBar(
    selectedSort: PhotosSortOption,
    onSortSelected: (PhotosSortOption) -> Unit,
    isOfflineEnabled: Boolean,
    onOfflineToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = selectedSort == PhotosSortOption.DEFAULT,
                onClick = { onSortSelected(PhotosSortOption.DEFAULT) },
                label = { Text("Default") }
            )
            FilterChip(
                selected = selectedSort == PhotosSortOption.AUTHOR,
                onClick = { onSortSelected(PhotosSortOption.AUTHOR) },
                label = { Text("Author") }
            )
            FilterChip(
                selected = selectedSort == PhotosSortOption.HEIGHT,
                onClick = { onSortSelected(PhotosSortOption.HEIGHT) },
                label = { Text("Height") }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier.padding(end = 4.dp),
                text = "Offline",
                style = MaterialTheme.typography.labelMedium
            )
            Switch(
                checked = isOfflineEnabled,
                onCheckedChange = onOfflineToggle
            )
        }
    }
}
