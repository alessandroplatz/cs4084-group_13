package com.example.cs4084_group_13;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

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
                backET.setEnabled(false);
                frontET.setEnabled(false);
                editBut.setEnabled(false);
                delBut.setEnabled(false);
                delBut.setBackgroundResource(R.drawable.onclickdelete);
                //disable editboxes
            }
        });

        delBut.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        delBut.setBackgroundResource(R.drawable.onclickdelete);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        delBut.setBackgroundResource(R.drawable.circle_deletebutton);
                        break;
                }
                return false;
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
                backET.setEnabled(true);
                frontET.setEnabled(true);
                editBut.setEnabled(true);
                delBut.setEnabled(true);
                delBut.setBackgroundResource(R.drawable.circle_deletebutton);
            }
        });

        editBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (backET.getText().toString().isEmpty()&&frontET.getText().toString().isEmpty()) {
                    Toast.makeText(EditFlashcardView.this,"Please don't Leave the Back and Front Empty!",Toast.LENGTH_SHORT).show();

                } else if (backET.getText().toString().isEmpty()) {

                    Toast.makeText(EditFlashcardView.this,"Please don't Leave the Back Empty!",Toast.LENGTH_SHORT).show();
                } else if (frontET.getText().toString().isEmpty()) {

                    Toast.makeText(EditFlashcardView.this,"Please don't Leave the Front Empty!",Toast.LENGTH_SHORT).show();
                } else {
                    DBHandler myDB = new DBHandler(EditFlashcardView.this);
                    myDB.updateFlashcard(flashcardid, frontET.getText().toString().trim(), backET.getText().toString().trim(), colid);
                    Intent intent1 = new Intent(EditFlashcardView.this, FlashCardView.class);
                    intent1.putExtra("collection_id", colid);
                    intent1.putExtra("collection_name", colname);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    finish();
                }
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}