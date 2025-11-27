package com.assignment.tcimageapp.core.internet

/**
 * Checks whether the device currently has internet connectivity.
 *
 * Used before making API requests and to determine behavior
 */
interface NetworkState {
    fun isOnline(): Boolean
}