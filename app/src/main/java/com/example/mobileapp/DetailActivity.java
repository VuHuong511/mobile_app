package com.example.mobileapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    RecyclerView recyclerView1;
    FloatingActionButton actionButton;
    MyDatabaseHelper db;
    ArrayList<String> idEx, trip_id, Type, Amount, Time;
    MyAdapterDetail adapterDetail;
    Button delete_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_acivity);

        recyclerView1 = findViewById(R.id.recyclerView1);
        actionButton = findViewById(R.id.actionButton);
//        String idEx = getIntent().getStringExtra("idEx");
//        String idTrip = getIntent().getStringExtra("trip_id");
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idTrip = getIntent().getStringExtra("tripId");
                Intent intent = new Intent(DetailActivity.this, AddDetailActivity.class);
                intent.putExtra("id", String.valueOf(idTrip));
                startActivity(intent);
            }
        });

        delete_detail = findViewById(R.id.delete_detail);
        delete_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });






        db = new MyDatabaseHelper(DetailActivity.this);

        idEx = new ArrayList<>();
        Type = new ArrayList<>();
        Amount = new ArrayList<>();
        Time = new ArrayList<>();
        trip_id = new ArrayList<>();

        displayDetail();

        adapterDetail = new MyAdapterDetail(DetailActivity.this,
                idEx,
                Type,
                Amount,
                Time,
                trip_id);
        recyclerView1.setAdapter(adapterDetail);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));


    }

    private void displayDetail() {
        String idTrip = getIntent().getStringExtra("tripId");
        Cursor cursor = db.getAllDetail(idTrip);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                idEx.add(cursor.getString(0));
                Type.add(cursor.getString(1));
                Amount.add(cursor.getString(2));
                Time.add(cursor.getString(3));
                trip_id.add(cursor.getString(4));
            }
        }
    }
    void alertDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + Type + " ?");
        builder.setMessage("Are you sure you want to delete " + Type + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(DetailActivity.this);
                myDB.deleteDetail(idEx);
                Intent intent = new Intent(DetailActivity.this,DetailActivity.class);
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