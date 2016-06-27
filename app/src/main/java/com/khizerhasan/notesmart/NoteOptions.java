package com.khizerhasan.notesmart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NoteOptions extends AppCompatActivity {

    String name ="";
    String number = "";
    String noteContent = "";
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_options);

        name=getIntent().getStringExtra("name");
        number = getIntent().getStringExtra("number");
        noteContent = getIntent().getStringExtra("noteContent");
        id = getIntent().getStringExtra("id");

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView number_view = (TextView) findViewById(R.id.phone_number_view1);
        number_view.setText(number);

        TextView name_view = (TextView) findViewById(R.id.textView3);
        name_view.setText(name);

        final EditText note_view = (EditText) findViewById(R.id.note_content1);
        note_view.setText(noteContent);

        Button save_button = (Button) findViewById(R.id.save_note_button1);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note_content_updated = note_view.getText().toString();
                DatabaseConnector d = new DatabaseConnector(getBaseContext());
                int res = d.updateNote(note_content_updated,id);
                if (res!=0){
                    Toast.makeText(NoteOptions.this, "Note Updated", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(NoteOptions.this, "Note Not Updated, There was some error. Check the log", Toast.LENGTH_SHORT).show();
                }


            }
        });


        Button delete_button = (Button) findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseConnector d = new DatabaseConnector(getBaseContext());
                int res = d.deleteThis(id);
                if (res!=0){
                    Toast.makeText(NoteOptions.this, "Note Deleted", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(NoteOptions.this, "Note Not Deleted, There was some error. Check the log", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
