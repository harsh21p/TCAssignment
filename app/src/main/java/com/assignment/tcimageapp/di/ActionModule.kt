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
        networkState: NetworkState,
        @LocalQualifier localPhotoRepository: PhotosRepository,
        @RemoteQualifier remotePhotoRepository: PhotosRepository
    ): GetPhotosAction {
        return GetPhotosAction(networkState,remotePhotoRepository,localPhotoRepository)
    }

    @Provides
    fun provideGetSelectedAuthorAction(
        photosRepository: AuthorRepository
    ): GetSelectedAuthorAction {
        return GetSelectedAuthorAction(photosRepository)
    }

    @Provides
    fun provideSaveSelectedAuthorAction(
        photosRepository: AuthorRepository
    ): SaveSelectedAuthorAction {
        return SaveSelectedAuthorAction(photosRepository)
    }
}