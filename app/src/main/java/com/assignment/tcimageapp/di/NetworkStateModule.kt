package com.assignment.tcimageapp.di

import com.assignment.tcimageapp.core.internet.NetworkMonitorImpl
import com.assignment.tcimageapp.core.internet.NetworkState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkStateModule {
    @Binds
    @Singleton
    abstract fun bindNetworkMonitor(
        impl: NetworkMonitorImpl
    ): NetworkState
}