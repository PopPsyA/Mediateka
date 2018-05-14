package com.ru.devit.mediateka.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ru.devit.mediateka.presentation.main.SyncConnectionListener;

public class ConnectionReceiver extends BroadcastReceiver {

    public static SyncConnectionListener connectionListener;

    @Override
    public void onReceive(Context context, Intent arg) {
        boolean isConnected = checkConnection(context);

        if (connectionListener != null) {
            connectionListener.onNetworkConnectionChanged(isConnected);
        }
    }

    public boolean isInternetConnected(Context context){
        return checkConnection(context);
    }

    private boolean checkConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
