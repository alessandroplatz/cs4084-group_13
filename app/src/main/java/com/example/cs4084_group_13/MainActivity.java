package com.example.cs4084_group_13;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHandler db;
    ArrayList<String> names,ids;
    RecyclerView recyclerView;
    CustomAdaptor customAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            recyclerView = findViewById(R.id.collGall);
            Button addButton = findViewById(R.id.button2);




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

            customAdaptor = new CustomAdaptor(MainActivity.this,ids,names);
            recyclerView.setAdapter(customAdaptor);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

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
                ids.add(cursor.getString(0));
                names.add(cursor.getString(1));
            }
        }
    }


}