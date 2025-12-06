package com.assignment.tcimageapp.mock

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Minimal in memory DataStore for Preferences used only in tests.
 * It supports:
 *  - data flow
 *  - updateData()
 */
class InMemoryPreferencesDataStore(
    initial: Preferences = emptyPreferences()
) : DataStore<Preferences> {

    private val state = MutableStateFlow(initial)

    override val data: Flow<Preferences>
        get() = state

    override suspend fun updateData(transform: suspend (Preferences) -> Preferences): Preferences {
        val newValue = transform(state.value)
        state.value = newValue
        return newValue
    }
}