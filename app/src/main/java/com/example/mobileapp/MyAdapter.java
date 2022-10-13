package com.example.mobileapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList id, expense_name, expense_destination, expense_date, expense_risk, expense_description;
    private ArrayList trip_id, Type, Amount, Time;

    private MyViewHolder holder;



    void adapterDetail (Context context, ArrayList trip_id, ArrayList Type,
                        ArrayList Amount, ArrayList Time){
        this.context = context;
        this.trip_id = trip_id;
        this.Type = Type;
        this.Amount = Amount;
        this.Time = Time;
    }



    public MyAdapter (Context context, ArrayList id, ArrayList expense_name,
                      ArrayList expense_destination, ArrayList expense_date,
                      ArrayList expense_risk, ArrayList expense_description){
        this.context = context;
        this.id = id;
        this.expense_name = expense_name;
        this.expense_destination = expense_destination;
        this.expense_date = expense_date;
        this.expense_risk = expense_risk;
        this.expense_description = expense_description;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {



        holder.textId.setText(String.valueOf(id.get(position)));
        holder.textName.setText(String.valueOf(expense_name.get(position)));
        holder.textDestination.setText(String.valueOf(expense_destination.get(position)));
        holder.textDate.setText(String.valueOf(expense_date.get(position)));
        holder.textRisk.setText(String.valueOf(expense_risk.get(position)));
      //  holder.textDescription.setText(String.valueOf(expense_description.get(position)));




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("name", String.valueOf(expense_name.get(position)));
                intent.putExtra("destination", String.valueOf(expense_destination.get(position)));
                intent.putExtra("date", String.valueOf(expense_date.get(position)));
                intent.putExtra("risk", String.valueOf(expense_risk.get(position)));
                intent.putExtra("description", String.valueOf(expense_description.get(position)));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textId, textName, textDestination, textDate, textRisk, textDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.textId);
            textName = itemView.findViewById(R.id.textName);
            textDestination = itemView.findViewById(R.id.textDestination);
            textDate = itemView.findViewById(R.id.textDate);
            textRisk = itemView.findViewById(R.id.textRisk);
            //textDescription = itemView.findViewById(R.id.textDescription);




        }
    }
}
