package com.example.android.popularmoviestestcareem;

import android.app.Application;

import com.example.android.popularmoviestestcareem.Receiver.ConnectionReceiver;

public class TestCareemApplication extends Application {

    private static TestCareemApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized TestCareemApplication getInstance() {
        return mInstance;
    }

    public void setConnectionListener(ConnectionReceiver.ConnectionReceiverListener listener) {
        ConnectionReceiver.connectionReceiverListener = listener;
    }
}
