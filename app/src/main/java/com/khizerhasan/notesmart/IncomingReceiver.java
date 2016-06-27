package com.khizerhasan.notesmart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by KhizerHasan on 4/10/2016.
 */
public class IncomingReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                String number = "";

                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        if(incomingNumber.startsWith("1"))
                        {
                            number = "+"+incomingNumber;
                        }
                        if(incomingNumber.startsWith("+")){
                            number = incomingNumber;
                        }

                        Log.d("IncomingNumber",number);
                        Intent in = new Intent(context, DialogActivity.class);
                        in.putExtra("number",number);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(in);


                        break;
                    case TelephonyManager.CALL_STATE_IDLE:

                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:

                        break;
                    default:
                        break;
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }
}