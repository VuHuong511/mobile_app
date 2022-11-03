package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddDetailActivity extends AppCompatActivity {


    EditText inputAmount, inputTime, inputTripId;
    TextView tripId;
    Button Add_button1;
    Intent intent;



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
//        tripId = findViewById(R.id.tripId);
//        inputTripId = findViewById(R.id.inputTripId);
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

        String tripId = getIntent().getStringExtra("id");


        Add_button1 = findViewById(R.id.Add_button1);
        Add_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Error()){
                    MyDatabaseHelper db = new MyDatabaseHelper(AddDetailActivity.this);
                    long result = db.AddDetail(
                            spinnerType.getSelectedItem().toString().trim(),
                            inputAmount.getText().toString().trim(),
                            inputTime.getText().toString().trim(),
                            tripId);
                    InputDetail();
                    if ( result == -1) {
                        Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "ADDED", Toast.LENGTH_LONG).show();
                    }
                    Intent i = new Intent(AddDetailActivity.this, DetailActivity.class);
                    i.putExtra("tripId", tripId);
                    startActivity(i);

                }
            }
        });
    }


    private void InputDetail(){
        spinnerType = findViewById(R.id.spinnerType);
        inputAmount = findViewById(R.id.inputAmount);
        inputTime = findViewById(R.id.inputTime);


        String spinner = spinnerType.getSelectedItem().toString();
        String amount = inputAmount.getText().toString();
        String time = inputTime.getText().toString();


        alertAddDetail(spinner, amount, time);
    }


    private void alertAddDetail(String spinnerType, String inputAmount, String inputTime){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Details expense")
                .setMessage("Details expense: "  +
                        "\n Name: " + spinnerType +
                        "\n Destination: " + inputAmount +
                        "\n Date: " + inputTime

                ).setNeutralButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(AddDetailActivity.this, DetailActivity.class);
                        startActivity(intent);
                    }
                });

        builder.create().show();

    }

    private boolean Error(){
        if(spinnerType.getSelectedItem().toString().isEmpty() |
                inputAmount.getText().toString().isEmpty() |
                inputTime.getText().toString().isEmpty()){
            Toast.makeText(AddDetailActivity.this, "You need to fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}