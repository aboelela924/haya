package com.bignerdranch.android.haya.broadcastRecivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class InternetConnectionBroadcastReciever extends BroadcastReceiver {

    public interface Networking{
        void startNetworking();
    }



    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
