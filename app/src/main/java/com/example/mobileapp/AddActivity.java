package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    EditText inputName, inputDestination, inputDate, inputDescription;
    Button Add_button;
    RadioButton radioSelect;
    RadioGroup radioGroup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        inputName = findViewById(R.id.inputName);
        inputDestination = findViewById(R.id.inputDestination);
        inputDate = findViewById(R.id.inputDate);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        inputDescription = findViewById(R.id.inputDescription);

        Add_button = findViewById(R.id.Add_button);
        Add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioSelect=(RadioButton)findViewById(selectedId);
                MyDatabaseHelper db = new MyDatabaseHelper(AddActivity.this);
                long resultAddDb = db.AddExpense(inputName.getText().toString(),
                        inputDestination.getText().toString(),
                        inputDate.getText().toString(),
                        radioSelect.getText().toString(),
                        inputDescription.getText().toString()
                );

                Input();



                if (resultAddDb == -1){
                    Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "ADDED", Toast.LENGTH_LONG).show();
                }
                Intent i = new Intent(AddActivity.this, MainActivity.class);
                startActivity(i);
            }

        });




    }

    private void Input(){
        inputName = findViewById(R.id.inputName);
        inputDestination = findViewById(R.id.inputDestination);
        inputDate = findViewById(R.id.inputDate);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioSelect=(RadioButton)findViewById(selectedId);
        inputDescription = findViewById(R.id.inputDescription);

  
        String name = inputName.getText().toString();
        String destination =inputDestination.getText().toString();
        String date = inputDate.getText().toString();
        String risk = radioSelect.getText().toString();
        String description = inputDescription.getText().toString();

        displayAdd(name, destination, date, risk, description);
    }

    private void displayAdd(String inputName , String inputDestination, String inputDate, String radioSelect, String inputDescription){
        new AlertDialog.Builder(this).setTitle("Details expense").setMessage("Details expense: " +
                "\n" + inputName +
                "\n" + inputDestination +
                "\n" + inputDate +
                "\n" + radioSelect +
                "\n" + inputDescription
        ).setNeutralButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }



    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void updateDate(LocalDate date) {
        TextView dateText = (TextView) findViewById(R.id.inputDate);
        dateText.setText(date.toString());
    }
}