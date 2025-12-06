package com.assignment.tcimageapp.mock

import com.assignment.tcimageapp.core.internet.NetworkState

/**
 * Simple mock network state class for testing.
 */
class NetworkMonitorFake(private val isOnlineVal: Boolean): NetworkState {
    override fun isOnline(): Boolean {
        return isOnlineVal
    }
}