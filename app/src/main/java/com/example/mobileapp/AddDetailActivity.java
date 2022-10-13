package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class AddDetailActivity extends AppCompatActivity {

    EditText inputAmount, inputTime;
    Button Add_button1;

    private final String[] typeArray = {
            "Food",
            "Entertainment",
            "Drink",
            "Travel"
    };
    private Spinner spinnerType;


    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail);

        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, typeArray);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter((dataAdapter));
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("dataAdapter", (String) adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        inputAmount = findViewById(R.id.inputAmount);
        inputTime = findViewById(R.id.inputTime);
        inputTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(AddDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "/" + month + "/" + year;
                        inputTime.setText(date);

                    }
                }, year, month, day);
                dialog.show();
            }
        });
        Add_button1 = findViewById(R.id.Add_button1);
        Add_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyDatabaseHelper db = new MyDatabaseHelper(AddDetailActivity.this);
                long resultAdd = db.AddDetail(inputAmount.getText().toString(),
                        inputTime.getText().toString(),
                        spinnerType.getSelectedItem().toString());

                if (resultAdd == -1) {
                    Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "ADDED", Toast.LENGTH_LONG).show();
                }
                Intent i = new Intent(AddDetailActivity.this, DetailActivity.class);
                startActivity(i);
            }
        });
    }
}