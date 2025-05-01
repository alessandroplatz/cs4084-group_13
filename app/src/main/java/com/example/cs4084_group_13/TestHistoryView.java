package com.example.cs4084_group_13;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class TestHistoryView extends AppCompatActivity implements RecyclerViewInterface {

    DBHandler db;
    ArrayList<String> name;
    ArrayList<String> dates;
    ArrayList<Integer> score;
    RecyclerView recyclerView;
    AdaptorForTest adaptorForTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_history_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView toolBar = findViewById(R.id.bottomNavigationView);
        toolBar.setSelectedItemId(R.id.TestHistory);

        db = new DBHandler(this);
        dates = new ArrayList<>();
        name = new ArrayList<>();
        score = new ArrayList<>();
        storeDataInArrays();
        adaptorForTest = new AdaptorForTest(this,name,score,dates,this);
        recyclerView.setAdapter(adaptorForTest);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

        toolBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.Collections)
                {
                    Intent intent = new Intent(TestHistoryView.this, MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.PopQuiz) {
                    Intent intent = new Intent(TestHistoryView.this, PopQuizView.class);
                    startActivity(intent);
                } else if (id == R.id.TestHistory) {
                    Intent intent = new Intent(TestHistoryView.this, TestHistoryView.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    void storeDataInArrays() {
        Cursor cursor = db.readAllTests();
        if (cursor.getCount() ==0) {
            Toast.makeText(this,"No Tests",Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                name.add(db.GetCollectionName(cursor.getInt(2)));
                dates.add(cursor.getString(2));
                score.add(cursor.getInt(3)/cursor.getInt(4));
            }
        }
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int position) {

    }
}