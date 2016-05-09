package com.example.administrator.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ZX on 2016/4/19.
 */
public class BootReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)){
            Intent mainAty=new Intent(context,MainActivity.class);
            mainAty.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainAty);

        }

    }
}
