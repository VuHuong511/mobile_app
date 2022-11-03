package com.example.mobileapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class UpdateActivity extends AppCompatActivity {

    EditText inputName, inputDestination, inputDescription;
    TextView inputDate;
    Button Update_button, Delete_button, all_expense_button;
    RadioGroup radioGroup;
    RadioButton radioSelect;

    MyDatabaseHelper myDB;
    String id, name, destination, date, risk, description;

    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        inputName = findViewById(R.id.inputName);
        inputDestination = findViewById(R.id.inputDestination);
        inputDate = findViewById(R.id.inputDate);
        inputDate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(UpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        inputDate.setText(date);

                    }
                },year, month,day);
                dialog.show();
            }
        });

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        inputDescription = (EditText)findViewById(R.id.inputDescription);
        Update_button = findViewById(R.id.Update_button);
        Delete_button = findViewById(R.id.Delete_button);
        all_expense_button = findViewById(R.id.all_expense_button);


        myDB = new MyDatabaseHelper(UpdateActivity.this);

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        destination = getIntent().getStringExtra("destination");
        date = getIntent().getStringExtra("date");
        risk = getIntent().getStringExtra("risk");
        if(risk.equals("Yes")){
            RadioButton yes = findViewById(R.id.radioButton);
            yes.setChecked(true);
        } else {
            RadioButton no = findViewById(R.id.radioButton2);
            no.setChecked(true);
        }
        description = getIntent().getStringExtra("description");


        inputName.setText(name);
        inputDestination.setText(destination);
        inputDate.setText(date);
        inputDescription.setText(description);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(name);
        }

        Update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int SelectID = radioGroup.getCheckedRadioButtonId();
                radioSelect = findViewById(SelectID);
                String radio = radioSelect.getText().toString();
                myDB.updateData(id, inputName.getText().toString(),
                        inputDestination.getText().toString(),
                        inputDate.getText().toString(),
                        risk = radio,
                        inputDescription.getText().toString());
                Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        });
        Delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
        all_expense_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateActivity.this, DetailActivity.class);
                intent.putExtra("tripId", id);
                startActivity(intent);
            }
        });
    }



    void confirmDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteRow(id);
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }



}