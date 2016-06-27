package com.khizerhasan.notesmart;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SaveNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_note);
        String numb = getIntent().getStringExtra("number").replaceAll("\\s","").replaceAll("-","").replaceAll("\\(","").replaceAll("\\)","");
        String number = "";

        if (numb.startsWith("+")){
            number = numb;
        }
        else if(numb.startsWith("1")){
            number = "+"+numb;
        }
        else{
            number = "+1"+numb;
        }

        final String name = getIntent().getStringExtra("name");

        TextView phoneNumberView = (TextView) findViewById(R.id.phone_number_view);
        phoneNumberView.setText(number);

        TextView nameView = (TextView)findViewById(R.id.name_view);
        nameView.setText(name);



        final EditText note_content = (EditText) findViewById(R.id.note_content);



            Button save_note = (Button) findViewById(R.id.save_note_button);
        final String finalNumber = number;
        save_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteContent = note_content.getText().toString();
                Calendar c = Calendar.getInstance();
                String time = c.getTime().toString();

                //saving the values to the database
                DatabaseConnector d= new DatabaseConnector(getBaseContext());
                int result = d.saveData(finalNumber,name,noteContent,time);
                if(result==1){
                    Toast.makeText(SaveNote.this,"DataSaved ",Toast.LENGTH_SHORT).show();
                }

                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
