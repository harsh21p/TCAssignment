package com.assignment.tcimageapp.core.internet


import java.io.IOException

class NoInternetException(
    message: String = "No internet connection"
) : IOException(message)