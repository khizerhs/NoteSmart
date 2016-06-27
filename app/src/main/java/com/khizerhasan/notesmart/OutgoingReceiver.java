package com.khizerhasan.notesmart;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by KhizerHasan on 4/10/2016.
 */
public class OutgoingReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {


        String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

        String outgoingNumber = "";
        if (number.startsWith("+")){
            outgoingNumber = number;
        }
        else if(number.startsWith("1")){
            outgoingNumber = "+"+number;
        }
        else{
            outgoingNumber = "+1"+number;
        }
        Log.d("TAG",outgoingNumber);


        Thread thread=  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        wait(3000);
                    }
                }
                catch(InterruptedException ex){
                }

                // TODO
            }
        };

        thread.start();


        Intent in = new Intent(context, DialogActivity.class);
        in.putExtra("number",outgoingNumber);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(in);


    }
}
