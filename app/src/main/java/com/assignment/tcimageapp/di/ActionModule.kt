package com.assignment.tcimageapp.di

import com.assignment.tcimageapp.core.internet.NetworkState
import com.assignment.tcimageapp.di.annotations.LocalQualifier
import com.assignment.tcimageapp.di.annotations.RemoteQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.assignment.tcimageapp.domain.action.GetPhotosAction
import com.assignment.tcimageapp.domain.repository.PhotosRepository

import com.assignment.tcimageapp.domain.action.GetSelectedAuthorAction
import com.assignment.tcimageapp.domain.action.SaveSelectedAuthorAction
import com.assignment.tcimageapp.domain.repository.AuthorRepository


@Module
@InstallIn(SingletonComponent::class)
object ActionModule {
    @Provides
    fun provideGetPhotosAction(
        networkMonitor: NetworkState,
        @RemoteQualifier remotePhotosRepository: PhotosRepository,
        @LocalQualifier localPhotoRepository: PhotosRepository
    ): GetPhotosAction {
        return GetPhotosAction(
            networkMonitor = networkMonitor,
            localPhotoRepository = localPhotoRepository,
            remotePhotoRepository = remotePhotosRepository
        )
    }

    @Provides
    fun provideGetSelectedAuthorAction(
        authorRepository: AuthorRepository
    ): GetSelectedAuthorAction {
        return GetSelectedAuthorAction(authorRepository)
    }

    @Provides
    fun provideSaveSelectedAuthorAction(
        authorRepository: AuthorRepository
    ): SaveSelectedAuthorAction {
        return SaveSelectedAuthorAction(authorRepository)
    }
}