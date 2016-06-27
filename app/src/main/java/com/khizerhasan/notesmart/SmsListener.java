package com.khizerhasan.notesmart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by KhizerHasan on 4/12/2016.
 */
public class SmsListener extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String number =  smsMessage.getOriginatingAddress();
                Log.d("SMSIncoming",number);

                Intent in = new Intent(context, DialogActivity.class);
                in.putExtra("number",number);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in);

            }
        }
    }
}
