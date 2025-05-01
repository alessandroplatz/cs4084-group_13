package com.example.cs4084_group_13;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PopQuizView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pop_quiz_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        Button btButton = findViewById(R.id.butBT);
        BottomNavigationView toolBar = findViewById(R.id.bottomNavigationView);
        toolBar.setSelectedItemId(R.id.PopQuiz);

        toolBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.Collections)
                {
                    Intent intent = new Intent(PopQuizView.this, MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.PopQuiz) {
                    Intent intent = new Intent(PopQuizView.this, PopQuizView.class);
                    startActivity(intent);
                } else if (id == R.id.TestHistory) {
                    Intent intent = new Intent(PopQuizView.this, TestHistoryView.class);
                    startActivity(intent);
                }
                return false;
            }
        });





        btButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler db = new DBHandler(PopQuizView.this);
                if (db.getFlashcardsForTestMode().size() < 10) {
                    Toast.makeText(PopQuizView.this,"Please add At least 10 Flashcards before Attempting Test Mode",Toast.LENGTH_SHORT).show();
                }
                 else {
                    Intent intent = new Intent(PopQuizView.this, PopQuizTestView.class);
                    startActivity(intent);
                }
            }
        });
    }


}