package com.assignment.tcimageapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Singleton
import dagger.hilt.components.SingletonComponent
import androidx.datastore.core.DataStore
import android.content.Context
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.assignment.tcimageapp.data.local.serializers.PhotosSerializer
import com.assignment.tcimageapp.data.remote.dto.CachedPhotos

private const val PREFS_NAME = "user_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = PREFS_NAME
)

private val Context.dataStoreCore by dataStore(
    fileName = PREFS_NAME,
    serializer = PhotosSerializer()
)

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providePreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.dataStore

    @Provides
    @Singleton
    fun provideCoreDataStore(
        @ApplicationContext context: Context
    ): DataStore<CachedPhotos> = context.dataStoreCore
}