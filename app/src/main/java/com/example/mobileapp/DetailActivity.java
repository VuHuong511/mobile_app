package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    RecyclerView recyclerView1;
    FloatingActionButton actionButton;

    MyDatabaseHelper db;
    ArrayList<String> trip_id, Type, Amount, Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_acivity);

        recyclerView1 = findViewById(R.id.recyclerView1);
        actionButton = findViewById(R.id.actionButton);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, AddDetailActivity.class);
                startActivity(intent);
            }
        });


        db = new MyDatabaseHelper(this);
        trip_id = new ArrayList<>();
        Type = new ArrayList<>();
        Amount = new ArrayList<>();
        Time = new ArrayList<>();

        displayDetail();
    }

    private void displayDetail() {
        Cursor cursor = db.getAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                trip_id.add(cursor.getString(0));
                Type.add(cursor.getString(1));
                Amount.add(cursor.getString(2));
                Time.add(cursor.getString(3));
            }
        }
    }
}