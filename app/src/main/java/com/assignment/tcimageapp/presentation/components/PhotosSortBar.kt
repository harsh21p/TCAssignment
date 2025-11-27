package com.assignment.tcimageapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.assignment.tcimageapp.core.PhotosSortOption

@Composable
fun PhotosSortBar(
    selected: PhotosSortOption,
    onSortSelected: (PhotosSortOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = selected == PhotosSortOption.DEFAULT,
            onClick = { onSortSelected(PhotosSortOption.DEFAULT) },
            label = { Text("Default") }
        )
        FilterChip(
            selected = selected == PhotosSortOption.AUTHOR_ASC,
            onClick = { onSortSelected(PhotosSortOption.AUTHOR_ASC) },
            label = { Text("Author ↑") }
        )
        FilterChip(
            selected = selected == PhotosSortOption.AUTHOR_DESC,
            onClick = { onSortSelected(PhotosSortOption.AUTHOR_DESC) },
            label = { Text("Author ↓") }
        )
    }
}
