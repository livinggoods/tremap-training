package com.expansion.lg.kimaru.training.receivers;

import android.app.Application;

/**
 * Created by kimaru on 2/23/18.
 */

public class TremapApp extends Application {
    public static TremapApp mInstance;
    @Override
    public void onCreate(){
        super.onCreate();
        mInstance = this;
    }

    public static synchronized TremapApp getmInstance(){
        return mInstance;
    }

    public void setConnectivityListener (ConnectivityReceiver.ConnectivityReceiverListener listener){
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
