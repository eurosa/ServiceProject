package com.example.serviceproject;

import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

import com.example.serviceproject.call.CallListener;
import com.example.serviceproject.call.PhoneCallReceiver;
import com.example.serviceproject.services.InfiniteService;
import com.example.serviceproject.sms.SmsObserver;
import com.example.serviceproject.sms.SmsReceivedListener;
import com.example.serviceproject.sms.SmsReceiver;
import com.example.serviceproject.sms.SmsSentListener;


import java.io.File;
import java.util.Date;

public class CallSmsService extends InfiniteService {

    public int onStartCommand(Intent intent, int flags, int startId) {
        int ret = super.onStartCommand(intent, flags, startId);
        // to detect received sms
        SmsReceiver.setSmsReceivedListener(new SmsReceivedListener() {
            @Override
            public void onMessageReceived(String number, String contactName, String messageText, long timestamp) {
                Log.e("sms received from", number);
            }
        });

        // to detect sent sms
        SmsObserver.setSmsSentListener(new SmsSentListener() {
            @Override
            public void onMessageSent(String number, String contactName, String messageText, long timestamp) {
                Log.e("sms sent to", number);
            }
        });

        // to detect calls
        PhoneCallReceiver.setCallListener(new CallListener() {
            @Override
            public void onIncomingCallStarted(Context ctx, String number, Date start, String contactName) {
                Log.e("incoming started", number);
            }

            @Override
            public void onIncomingCallAnswered(Context ctx, String number, Date start, String contactName) {
                Log.e("incoming answered", number);
            }

            @Override
            public void onOutgoingCallStarted(Context ctx, String number, Date start, String contactName) {
                Log.e("outgoing started", number);
            }

            @Override
            public void onIncomingCallEnded(Context ctx, String number, Date start, Date end, File recordedFile) {
                Log.e("incoming ended", number);
            }

            @Override
            public void onOutgoingCallEnded(Context ctx, String number, Date start, Date end, File recordedFile) {
                Log.e("outgoing ended", number);
            }

            @Override
            public void onMissedCall(Context ctx, String number, Date start, String contactName) {
                Log.e("missed call", number);
                SmsManager smsManager = SmsManager.getDefault();
                if (number.contains(getString(R.string.check_for_number)))
                    smsManager.sendTextMessage(getString(R.string.send_sms_to_contact),
                            null,
                            getString(R.string.send_message) + contactName + " " + number,
                            null,
                            null);
            }
        });

        return ret;
    }
}
