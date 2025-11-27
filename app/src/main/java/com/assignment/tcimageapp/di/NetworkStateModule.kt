package com.assignment.tcimageapp.di

import dagger.Module
import dagger.hilt.InstallIn
import jakarta.inject.Singleton
import dagger.Binds
import dagger.hilt.components.SingletonComponent

import com.assignment.tcimageapp.core.internet.NetworkStateImpl
import com.assignment.tcimageapp.core.internet.NetworkState

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkStateModule {
    @Binds
    @Singleton
    abstract fun bindNetworkMonitor(
        impl: NetworkStateImpl
    ): NetworkState
}