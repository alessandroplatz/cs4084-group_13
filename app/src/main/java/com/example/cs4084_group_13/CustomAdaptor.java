package com.example.cs4084_group_13;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdaptor extends RecyclerView.Adapter<CustomAdaptor.MyViewHolder> {

    private Context context;
    private ArrayList<String> names,ids;

    public CustomAdaptor(Context context,ArrayList<String>ids,ArrayList<String>names) {
        this.context = context;
        this.ids = ids;
        this.names = names;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdaptor.MyViewHolder holder, int position) {
        holder.collID.setText(String.valueOf(ids.get(position)));
        holder.collName.setText(String.valueOf(names.get(position)));


    }

    @Override
    public int getItemCount() {
        return ids.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

         TextView collID,collName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            collID = itemView.findViewById(R.id.collID);
            collName = itemView.findViewById(R.id.collName);
        }
    }
}
