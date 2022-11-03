package com.example.mobileapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterDetail extends RecyclerView.Adapter<MyAdapterDetail.MyViewHolder> {
    private Context context;
    private ArrayList id, Type, Amount, Time, trip_id;


    public MyAdapterDetail(Context context,
                           ArrayList id,
                           ArrayList Type,
                           ArrayList Amount,
                           ArrayList Time,
                           ArrayList trip_id) {

        this.context = context;
        this.id = id;
        this.Type = Type;
        this.Amount = Amount;
        this.Time = Time;
        this.trip_id = trip_id;

    }

    @NonNull
    @Override
    public MyAdapterDetail.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_detail, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterDetail.MyViewHolder holder, int position) {
        holder.textId1.setText(String.valueOf(id.get(position)));
        holder.textName1.setText(String.valueOf(Type.get(position)));
        holder.textAmount.setText(String.valueOf(Amount.get(position)));
        holder.textDate1.setText(String.valueOf(Time.get(position)));
//        holder.tripId.setText(String.valueOf(trip_id.get(position)));
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textId1, textName1, textAmount, textDate1, tripId;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textId1 = itemView.findViewById(R.id.textId1);
            textName1 = itemView.findViewById(R.id.textName1);
            textAmount = itemView.findViewById(R.id.textAmount);
            textDate1 = itemView.findViewById(R.id.textDate1);
//            tripId = itemView.findViewById(R.id.tripId);
        }
    }
}
