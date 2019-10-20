package com.maryang.testablearc.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkHelper(context: Context) {

    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isConnected(): Boolean =
        connectivityManager.isConnected()
}

fun ConnectivityManager.isConnected(): Boolean {
    val capabilities = getNetworkCapabilities(activeNetwork)
    return capabilities?.run {
        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    } ?: false
}
