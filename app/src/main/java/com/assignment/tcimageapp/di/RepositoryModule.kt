package com.assignment.tcimageapp.di

import com.assignment.tcimageapp.data.repository.LocalAuthorRepository
import com.assignment.tcimageapp.data.repository.LocalPhotoRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.Binds

import dagger.hilt.components.SingletonComponent
import com.assignment.tcimageapp.domain.repository.PhotosRepository

import com.assignment.tcimageapp.data.repository.RemotePhotosRepository
import com.assignment.tcimageapp.di.annotations.LocalQualifier
import com.assignment.tcimageapp.di.annotations.RemoteQualifier
import com.assignment.tcimageapp.domain.repository.AuthorRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @RemoteQualifier
    @Binds
    abstract fun bindRemotePhotosRepository(
        impl: RemotePhotosRepository
    ): PhotosRepository

    @LocalQualifier
    @Binds
    abstract fun bindLocalPhotosRepository(
        impl: LocalPhotoRepository
    ): PhotosRepository

    @Binds
    abstract fun bindAuthorRepository(
        impl: LocalAuthorRepository
    ): AuthorRepository

}