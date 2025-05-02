package com.example.cs4084_group_13;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{

    DBHandler db;
    ArrayList<String> names;
    ArrayList<Integer> ids;
    RecyclerView recyclerView;
    CustomAdaptor customAdaptor;
    SearchHandler searchHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


            recyclerView = findViewById(R.id.collGall);
            Button addButton = findViewById(R.id.button2);
            SearchView searchView = findViewById(R.id.searchView);
            RecyclerViewInterface recyclerViewInterface;
            BottomNavigationView toolBar = findViewById(R.id.bottomNavigationView);
            toolBar.setSelectedItemId(R.id.Collections);

            toolBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    if(id == R.id.Collections)
                    {
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else if (id == R.id.PopQuiz) {
                        Intent intent = new Intent(MainActivity.this, PopQuizView.class);
                        startActivity(intent);
                    } else if (id == R.id.TestHistory) {
                        Intent intent = new Intent(MainActivity.this, TestHistoryView.class);
                        startActivity(intent);
                    }
                    return false;
                }
            });
            addButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            addButton.setBackgroundResource(R.drawable.add_button_clicked);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            addButton.setBackgroundResource(R.drawable.add_collections_button);
                    }
                    return false;
                }
            });

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

            searchHandler = new SearchHandler(customAdaptor, ids, names);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchHandler.filter(newText.trim());
                return true;
            }
        });


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