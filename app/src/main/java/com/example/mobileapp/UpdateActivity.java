package com.example.mobileapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

public class UpdateActivity extends AppCompatActivity {

    EditText inputName, inputDestination, inputDate, editrisk;
    Button Update_button, Delete_button;
    RadioGroup radioGroup;
    MyDatabaseHelper myDB;
    String id, name, destination, date, risk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        inputName = findViewById(R.id.inputName);
        inputDestination = findViewById(R.id.inputDestination);
        inputDate = findViewById(R.id.inputDate);
        editrisk = (EditText)findViewById(R.id.editrisk);


        Update_button = findViewById(R.id.Update_button);
        Delete_button = findViewById(R.id.Delete_button);


        myDB = new MyDatabaseHelper(UpdateActivity.this);

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        destination = getIntent().getStringExtra("destination");
        date = getIntent().getStringExtra("date");
        risk = getIntent().getStringExtra("risk");




        inputName.setText(name);
        inputDestination.setText(destination);
        inputDate.setText(date);
        editrisk.setText(risk);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(name);
        }

        Update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB.updateData(id, inputName.getText().toString(),
                        inputDestination.getText().toString(),
                        inputDate.getText().toString(),
                        editrisk.getText().toString());
                Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(i);


            }
        });
        Delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDB.deleteRow(id);
                Toast.makeText(UpdateActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }








}