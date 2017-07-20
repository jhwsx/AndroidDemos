package com.wzc.sclockernotificationdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by wzc on 2017/7/19.
 */

public class MyReceiver extends BroadcastReceiver {

    public static final String ACTION_MYRECEIVER = "com.sclocker.ACTION_MYRECEIVER";


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && ACTION_MYRECEIVER.equals(intent.getAction())) {
            Toast.makeText(context, "hello my receiver", Toast.LENGTH_SHORT).show();
        }
    }
}
