package com.khizerhasan.notesmart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

/**
 * Created by KhizerHasan on 4/11/2016.
 */
public class DialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String number = getIntent().getStringExtra("number");

        DatabaseConnector d = new DatabaseConnector(this);
        List<NoteItem> noteItems = d.getNotesFor(number);
        if (noteItems.size() == 0){
            finish();
        }
        else {
            final StringBuilder message = new StringBuilder();
            int count = 1;
            for (NoteItem i : noteItems) {
                message.append(count + ". " + i.getNote() + "\n");
                count++;
            }

            final NoteItem item = noteItems.get(0);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(message.toString())
                    .setTitle("Smart Note for " + item.getName());
            builder.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(
                            getBaseContext(),
                            0,
                            myIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);


                    Notification.Builder mBuilder =
                            new Notification.Builder(getBaseContext())
                                    .setSmallIcon(R.drawable.ic_stat_name)
                                    .setContentTitle("Smart Note for " + item.getName())
                                    .setContentText(message.toString())
                                    .setContentIntent(pendingIntent);;

                    // Sets an ID for the notification
                    int mNotificationId = 1509;
                    // Gets an instance of the NotificationManager service
                    NotificationManager mNotifyMgr =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    // Builds the notification and issues it.
                    mNotifyMgr.notify(mNotificationId, mBuilder.build());
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            try {
                synchronized (this) {
                 wait(3000);
                    dialog.show();
                }
            } catch (InterruptedException e) {
                Log.d("WAIT",e.getMessage());

            }

        }
    }
}
