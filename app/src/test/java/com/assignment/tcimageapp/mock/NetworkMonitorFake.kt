package com.assignment.tcimageapp.mock

import com.assignment.tcimageapp.core.internet.NetworkState

class NetworkMonitorFake(private val isOnlineVal: Boolean): NetworkState {
    override fun isOnline(): Boolean {
        return isOnlineVal
    }
}