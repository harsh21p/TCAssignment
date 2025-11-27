package com.assignment.tcimageapp.data.local

import javax.inject.Singleton
import javax.inject.Inject

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.edit

/**
* Provides persistent storage for selected author.
*
* Uses - Jetpack DataStore.
 */
@Singleton
class AuthorFilterDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private object Keys {
        val SELECTED_AUTHOR = stringPreferencesKey("selected_author")
    }

    val selectedAuthorFlow: Flow<String?> =
        dataStore.data.map { prefs ->
            prefs[Keys.SELECTED_AUTHOR]
        }

    suspend fun setSelectedAuthor(author: String?) {
        dataStore.edit { prefs ->
            if (author.isNullOrEmpty()) {
                prefs.remove(Keys.SELECTED_AUTHOR)
            } else {
                prefs[Keys.SELECTED_AUTHOR] = author
            }
        }
    }
}
