package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    EditText inputName, inputDestination, inputDescription;
    TextView inputDate;
    Button Add_button;
    RadioButton radioSelect, radioButton, radioButton2;
    RadioGroup radioGroup;

    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        inputName = findViewById(R.id.inputName);
        inputDestination = findViewById(R.id.inputDestination);
        inputDate = findViewById(R.id.inputDate);
        inputDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "/" + month + "/" + year;
                        inputDate.setText(date);

                    }
                }, year, month, day);
                dialog.show();
            }
        });

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButton = (RadioButton) findViewById(R.id.radioButton) ;
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2) ;
        inputDescription = findViewById(R.id.inputDescription);

        Add_button = findViewById(R.id.Add_button);
        Add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alertError()) {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    radioSelect = (RadioButton) findViewById(selectedId);
                    MyDatabaseHelper db = new MyDatabaseHelper(AddActivity.this);
                    long resultAddDb = db.AddExpense(inputName.getText().toString(),
                            inputDestination.getText().toString(),
                            inputDate.getText().toString(),
                            radioSelect.getText().toString(),
                            inputDescription.getText().toString()
                    );
                    Input();

                    if (resultAddDb == -1) {
                        Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "ADDED", Toast.LENGTH_LONG).show();
                    }
                    /*Intent i = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(i);*/

                }
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

        alertAdd(name, destination, date, risk, description);
    }

    private void alertAdd(String inputName , String inputDestination,
                          String inputDate, String radioSelect, String inputDescription){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Details expense")
                .setMessage("Details expense: "  +
                "\n Name: " + inputName +
                "\n Destination: " + inputDestination +
                "\n Date: " + inputDate +
                "\n Requires risks assessment: " + radioSelect +
                "\n Description: " + inputDescription
        ).setNeutralButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        builder.create().show();

    }

    private boolean alertError(){
        if(inputName.getText().toString().isEmpty() |
                inputDestination.getText().toString().isEmpty() |
                inputDate.getText().toString().isEmpty() |
                !radioButton.isChecked() && (!radioButton2.isChecked())){
            Toast.makeText(AddActivity.this, "You need to fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}