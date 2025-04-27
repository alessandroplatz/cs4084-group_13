package com.example.cs4084_group_13;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{

    DBHandler db;
    ArrayList<String> names;
    ArrayList<Integer> ids;
    RecyclerView recyclerView;
    CustomAdaptor customAdaptor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


            recyclerView = findViewById(R.id.collGall);
            Button addButton = findViewById(R.id.button2);
            RecyclerViewInterface recyclerViewInterface;





            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, CreateCollectionActivity.class);
                    startActivity(intent);
                }
            });




            db = new DBHandler(MainActivity.this);
            names = new ArrayList<>();
            ids = new ArrayList<>();
            storeDataInArrays();

            customAdaptor = new CustomAdaptor(MainActivity.this,ids,names,this);
            recyclerView.setAdapter(customAdaptor);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));



            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            addButton.bringToFront();
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });


        }
    void storeDataInArrays() {
        Cursor cursor = db.readAllData();
        if (cursor.getCount() ==0) {
            Toast.makeText(this,"No Collections",Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String s = cursor.getString(0);
                ids.add(cursor.getInt(0));
                names.add(cursor.getString(1));
            }
        }
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, FlashCardView.class);
        intent.putExtra("collection_id",ids.get(position));
        intent.putExtra("collection_name",names.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {
        Intent intent = new Intent(MainActivity.this,EditCollectionView.class);
        intent.putExtra("collection_name",names.get(position));
        intent.putExtra("collection_id",ids.get(position));
        startActivity(intent);
    }
}