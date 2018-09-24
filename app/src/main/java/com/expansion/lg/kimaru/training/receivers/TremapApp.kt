package com.expansion.lg.kimaru.training.receivers

import android.app.Application

/**
 * Created by kimaru on 2/23/18.
 */

class TremapApp : Application() {
    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    fun setConnectivityListener(listener: ConnectivityReceiver.ConnectivityReceiverListener) {
        ConnectivityReceiver.connectivityReceiverListener = listener
    }

    companion object {
        var mInstance: TremapApp

        @Synchronized
        fun getmInstance(): TremapApp {
            return mInstance
        }
    }
}
