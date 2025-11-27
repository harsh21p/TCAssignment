package com.assignment.tcimageapp.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.Binds

import dagger.hilt.components.SingletonComponent
import com.assignment.tcimageapp.domain.repository.PhotosRepository

import com.assignment.tcimageapp.data.repository.PhotosRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindPhotosRepository(
        impl: PhotosRepositoryImpl
    ): PhotosRepository
}