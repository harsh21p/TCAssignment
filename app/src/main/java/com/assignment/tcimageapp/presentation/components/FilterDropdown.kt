package com.assignment.tcimageapp.presentation.feature.photos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropdown(
    authors: List<String>,
    selectedAuthor: String?,
    onAuthorSelected: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val allOptionLabel = "All authors"
    val currentText = selectedAuthor ?: allOptionLabel
    val hasActiveFilter = selectedAuthor != null

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp, top = 15.dp, bottom = 8.dp)
    ) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = currentText,
                onValueChange = { },
                readOnly = true,
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.FilterList,
                        contentDescription = "Filter",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingIcon = {
                    Box(
                        modifier = Modifier.width(56.dp).padding(end = 10.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)

                        if (hasActiveFilter) {
                            IconButton(
                                onClick = {
                                    onAuthorSelected(null)
                                    expanded = false
                                },
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "Clear filter",
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                },
                shape = MaterialTheme.shapes.large,
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                textStyle = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(allOptionLabel) },
                    onClick = {
                        expanded = false
                        onAuthorSelected(null)
                    }
                )

                authors.forEach { author ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = author,
                                style = if (author == selectedAuthor) {
                                    MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                } else {
                                    MaterialTheme.typography.bodyMedium
                                }
                            )
                        },
                        onClick = {
                            expanded = false
                            onAuthorSelected(author)
                        }
                    )
                }
            }
        }
    }
}
