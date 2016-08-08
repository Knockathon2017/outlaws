package com.juster.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

public class LocalSyncBroadcast {
    private LocalBroadcastManager mBroadcaster;

    public LocalSyncBroadcast(Context context) {
        mBroadcaster = LocalBroadcastManager.getInstance(context);
    }

    public void broadcastIntentWithAction(String action, Intent intent) {
        // Broadcasts the Intent
        intent.setAction(action);
        mBroadcaster.sendBroadcast(intent);
    }

    public void registerReceiver(BroadcastReceiver receiver, IntentFilter intentFilter) {
        mBroadcaster.registerReceiver(receiver, intentFilter);
    }

    public void unregisterReceiver(BroadcastReceiver receiver) {
        mBroadcaster.unregisterReceiver(receiver);
    }
}
