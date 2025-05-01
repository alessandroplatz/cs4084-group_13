package com.example.cs4084_group_13;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class TestHistoryView extends AppCompatActivity {

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
}