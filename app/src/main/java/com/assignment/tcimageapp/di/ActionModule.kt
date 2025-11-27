package com.assignment.tcimageapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.assignment.tcimageapp.domain.action.GetPhotosAction
import com.assignment.tcimageapp.domain.repository.PhotosRepository

import com.assignment.tcimageapp.domain.action.GetSelectedAuthorAction
import com.assignment.tcimageapp.domain.action.SaveSelectedAuthorAction


@Module
@InstallIn(SingletonComponent::class)
object ActionModule {
    @Provides
    fun provideGetPhotosAction(
        photosRepository: PhotosRepository
    ): GetPhotosAction {
        return GetPhotosAction(photosRepository)
    }

    @Provides
    fun provideGetSelectedAuthorAction(
        photosRepository: PhotosRepository
    ): GetSelectedAuthorAction {
        return GetSelectedAuthorAction(photosRepository)
    }

    @Provides
    fun provideSaveSelectedAuthorAction(
        photosRepository: PhotosRepository
    ): SaveSelectedAuthorAction {
        return SaveSelectedAuthorAction(photosRepository)
    }
}