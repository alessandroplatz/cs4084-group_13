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

public class AdaptorForFlashcard extends RecyclerView.Adapter<AdaptorForFlashcard.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    private Context context;
    private ArrayList<String> fronts,backs;
    private ArrayList<Integer> ids,colIDs;

    public AdaptorForFlashcard(Context context,ArrayList<Integer>ids,ArrayList<String>fronts,
                         ArrayList<String>backs,ArrayList<Integer>colIDs,
                         RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.ids = ids;
        this.fronts = fronts;
        this.backs = backs;
        this.colIDs =colIDs;
        this.recyclerViewInterface =recyclerViewInterface;
    }

    @NonNull
    @Override
    public AdaptorForFlashcard.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.flashcard_row,parent,false);
        return new MyViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.colFront.setText(String.valueOf(fronts.get(position)));
        holder.colBack.setText(String.valueOf(backs.get(position)));


    }

    @Override
    public int getItemCount() {
        return ids.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView colBack,colFront;

        public MyViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            colBack = itemView.findViewById(R.id.colBack);
            colFront = itemView.findViewById(R.id.colFront);
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
