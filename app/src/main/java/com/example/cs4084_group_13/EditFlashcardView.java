package com.example.cs4084_group_13;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditFlashcardView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_flashcard_view);


        Intent intent = getIntent();
        int colid = intent.getIntExtra("collection_id",0);
        int flashcardid = intent.getIntExtra("flashcard_id",0);
        String colname = intent.getStringExtra("collection_name");
        String front = intent.getStringExtra("front");
        String back = intent.getStringExtra("back");

        EditText frontET = findViewById(R.id.ETFront);
        EditText backET = findViewById(R.id.ETBack);
        Button editBut = findViewById(R.id.subBut);
        Button yesbut = findViewById(R.id.yesbut);
        Button nobut = findViewById(R.id.nobut);
        ImageButton delBut = findViewById(R.id.deletebut);
        LinearLayout confirmbox = findViewById(R.id.confirmbox);
        frontET.setText(front);
        backET.setText(back);

        delBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmbox.setVisibility(View.VISIBLE);
                //disable editboxes
            }
        });

        yesbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler myDB = new DBHandler(EditFlashcardView.this);
                myDB.deleteFlashcard(flashcardid);
                Intent intent1 = new Intent(EditFlashcardView.this,FlashCardView.class);
                intent1.putExtra("collection_id",colid);
                intent1.putExtra("collection_name",colname);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();
            }
        });

        nobut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmbox.setVisibility(View.INVISIBLE);
                //enable the editboxes
            }
        });

        editBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler myDB = new DBHandler(EditFlashcardView.this);
                myDB.updateFlashcard(flashcardid,frontET.getText().toString().trim(),backET.getText().toString().trim(),colid);
                Intent intent1 = new Intent(EditFlashcardView.this, FlashCardView.class);
                intent1.putExtra("collection_id",colid);
                intent1.putExtra("collection_name",colname);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}