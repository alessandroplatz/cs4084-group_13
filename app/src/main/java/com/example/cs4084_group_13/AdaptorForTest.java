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

public class AdaptorForTest extends RecyclerView.Adapter<AdaptorForTest.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    private Context context;
    private ArrayList<String> test_name, dates;
    private ArrayList<Integer> score;



    public AdaptorForTest(Context context,ArrayList<String> NAME,ArrayList<Integer> score,ArrayList<String> dates,
                             RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.test_name = NAME;
        this.dates = dates;
        this.score =score;

        this.recyclerViewInterface =recyclerViewInterface;
    }

    @NonNull
    @Override
    public AdaptorForTest.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.answer_row,parent,false);
        return new MyViewHolder(view,recyclerViewInterface);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(String.valueOf(test_name.get(position)));
        holder.score.setText(String.valueOf(score.get(position)));
        holder.date.setText(String.valueOf(dates.get(position)));
    }

    @Override
    public int getItemCount() {
        return score.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,score, date;
        View square;

        public MyViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            name = itemView.findViewById(R.id.CollName);
            score = itemView.findViewById(R.id.Score);
            date = itemView.findViewById(R.id.date);
            square = itemView.findViewById(R.id.square);
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
