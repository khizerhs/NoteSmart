package com.khizerhasan.notesmart;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity   {

    static final int PICK_CONTACT_REQUEST = 1;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission("android.permission.READ_CONTACTS")!=PackageManager.PERMISSION_GRANTED){
                String [] a = {"android.permission.READ_CONTACTS"};
                requestPermissions(a,100);
            }
            if (checkSelfPermission("android.permission.READ_PHONE_STATE")!=PackageManager.PERMISSION_GRANTED){
                String [] a = {"android.permission.READ_PHONE_STATE"};
                requestPermissions(a,200);
            }
            if (checkSelfPermission("android.permission.PROCESS_OUTGOING_CALLS")!=PackageManager.PERMISSION_GRANTED){
                String [] a = {"android.permission.PROCESS_OUTGOING_CALLS"};
                requestPermissions(a,300);
            }
            if (checkSelfPermission("android.permission.RECEIVE_SMS")!=PackageManager.PERMISSION_GRANTED){
                String [] a = {"android.permission.RECEIVE_SMS"};
                requestPermissions(a,400);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch(requestCode){
            case 100:
              if(grantResults[0]== PackageManager.PERMISSION_GRANTED ){
                  SharedPreferences pref = getSharedPreferences("PermissionsPref",0);
                  SharedPreferences.Editor edit = pref.edit();
                  edit.putBoolean("readContacts", true);
                  edit.commit();
              }
                break;
            case 200:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    SharedPreferences pref = getSharedPreferences("PermissionsPref",0);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putBoolean("readPhoneState", true);
                    edit.commit();
                }
                break;
            case 300:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    SharedPreferences pref = getSharedPreferences("PermissionsPref",0);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putBoolean("readOutgoingCalls", true);
                    edit.commit();
                }
                break;
            case 400:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    SharedPreferences pref = getSharedPreferences("PermissionsPref",0);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putBoolean("readSMS", true);
                    edit.commit();
                }
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        flag = 0;
        Button add_button = (Button) findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    flag =1;
                    Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                    pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                    startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);


                }
            });


        final ListView notesList = (ListView) findViewById(R.id.listView);
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        final DatabaseConnector d = new DatabaseConnector(getBaseContext());
        final List<NoteItem> list = d.getAllNotes();
        for (NoteItem item : list ) {
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("contact", item.getName()+" <"+item.getNumber()+">");
            datum.put("note", item.getNote());
            data.add(datum);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"contact", "note"},
                new int[] {android.R.id.text1,
                        android.R.id.text2});

        notesList.setAdapter(adapter);
        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoteItem i = list.get(position);

                Intent inte = new Intent(MainActivity.this,NoteOptions.class);
                inte.putExtra("number",i.getNumber());
                inte.putExtra("name",i.getName());
                inte.putExtra("noteContent",i.getNote());
                inte.putExtra("id",i.getId());

                startActivity(inte);

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (flag==0) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.Data.DISPLAY_NAME};

                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                cursor.moveToFirst();



                // Retrieve the phone number from the NUMBER column
                int column1 = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                int column2 = cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME);



                String number = cursor.getString(column1);
                String name = cursor.getString(column2);
                Intent saveNoteIntent = new Intent(MainActivity.this,SaveNote.class);
                saveNoteIntent.putExtra("number",number);
                saveNoteIntent.putExtra("name",name);
                startActivity(saveNoteIntent);

            }
        }
    }
}
