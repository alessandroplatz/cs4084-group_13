package com.example.cs4084_group_13;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Timer;

public class CustomAdaptor extends RecyclerView.Adapter<CustomAdaptor.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    private Context context;
    private ArrayList<String> names;

    private ArrayList<Integer> ids;
    public CustomAdaptor(Context context,ArrayList<Integer>ids,ArrayList<String>names,
                         RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.ids = ids;
        this.names = names;
        this.recyclerViewInterface =recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdaptor.MyViewHolder holder, int position) {
        holder.collName.setText(String.valueOf(names.get(position)));



    }

    public void updateData(ArrayList<Integer> newIds, ArrayList<String> newNames) {
        this.ids.clear();
        this.names.clear();
        this.ids.addAll(newIds);
        this.names.addAll(newNames);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ids.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

         TextView collName,collids;

            public MyViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
                super(itemView);

                collName = itemView.findViewById(R.id.collName);
                itemView.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        if (recyclerViewInterface!= null){
                            int pos = getAdapterPosition();

                            if (pos != RecyclerView.NO_POSITION) {
                                recyclerViewInterface.onItemClick(pos);
                            }
                        }
                    }

                });
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (recyclerViewInterface!= null) {
                            int pos = getAdapterPosition();

                            if (pos!= RecyclerView.NO_POSITION) {
                                recyclerViewInterface.onItemLongClick(pos);
                            }
                        }
                        return true;
                    }
                });

                itemView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        ConstraintLayout csl = view.findViewById(R.id.CSL);

                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                csl.setBackgroundColor(Color.GRAY);
                                break;
                            case MotionEvent.ACTION_UP:
                            case MotionEvent.ACTION_CANCEL:
                                csl.setBackgroundColor(Color.TRANSPARENT);
                                break;
                        }
                        return false;
                    }
                });
            }
        }
}
