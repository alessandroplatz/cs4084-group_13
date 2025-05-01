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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class ReviewAnswers extends AppCompatActivity implements RecyclerViewInterface{

    AdaptorForAnswers adaptorForAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review_answers);
        ArrayList<Answer> answers = (ArrayList<Answer>) ( getIntent().getBundleExtra("answers").getSerializable("key"));
        BottomNavigationView toolBar = findViewById(R.id.bottomNavigationView);
        ArrayList<Answer> wrong_answers = new ArrayList<>();
        ArrayList<Answer> correct_answers = new ArrayList<>();
        for (Answer element : answers) {
            if (element.getCorrect()) { correct_answers.add(element);}
            else { wrong_answers.add(element); }
        }
        answers.clear();
        answers.addAll(wrong_answers);
        answers.addAll(correct_answers);

        RecyclerView recyclerView = findViewById(R.id.collGall);
        adaptorForAnswers = new AdaptorForAnswers(this,answers,this);
        recyclerView.setAdapter(adaptorForAnswers);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

        toolBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.Collections)
                {
                    Intent intent = new Intent(ReviewAnswers.this, MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.PopQuiz) {
                    Intent intent = new Intent(ReviewAnswers.this, PopQuizView.class);
                    startActivity(intent);
                } else if (id == R.id.TestHistory) {
                    Intent intent = new Intent(ReviewAnswers.this, TestHistoryView.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int position) {

    }
}