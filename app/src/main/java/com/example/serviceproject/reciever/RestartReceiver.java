package com.example.serviceproject.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.serviceproject.CallSmsService;
import com.example.serviceproject.services.InfiniteService;


/**
 * Created by admin on 08-04-2019.
 */

public class RestartReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("RestartReceiver", "broadcast received");
        context.startService(new Intent(context.getApplicationContext(), InfiniteService.class));
       // context.startService(new Intent(context.getApplicationContext(), CallSmsService.class));

    }
}
