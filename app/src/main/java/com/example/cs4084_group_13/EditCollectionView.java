package com.example.cs4084_group_13;

import android.content.Intent;
import android.net.Uri;
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
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.net.URI;

public class EditCollectionView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_collection_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        int id = intent.getIntExtra("collection_id",0);
        String name = intent.getStringExtra("collection_name");
        EditText nameField = findViewById(R.id.enterNameInput);
        nameField.setText(name);
        Button addBut = findViewById(R.id.add_button);
        ImageButton delBut = findViewById(R.id.deletebutt);
        LinearLayout confirmbox = findViewById(R.id.confirmbox);
        Button yesbut = findViewById(R.id.yesbut);
        Button nobut = findViewById(R.id.nobut);
        ImageButton shareButton = findViewById(R.id.shareBut);

        nobut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmbox.setVisibility(View.INVISIBLE);
                delBut.setEnabled(true);
                nameField.setEnabled(true);
                addBut.setEnabled(true);
                delBut.setBackgroundResource(R.drawable.circle_deletebutton);
            }
        });

        yesbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(EditCollectionView.this,MainActivity.class);
                DBHandler db = new DBHandler(EditCollectionView.this);
                db.deleteCollection(intent.getIntExtra("collection_id",0));
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();
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

        delBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmbox.setVisibility(View.VISIBLE);
                delBut.setEnabled(false);
                nameField.setEnabled(false);
                addBut.setEnabled(false);
                delBut.setBackgroundResource(R.drawable.onclickdelete);
            }
        });

        addBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameField.getText().toString().isEmpty()) {
                    Toast.makeText(EditCollectionView.this,"Please enter something!",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent1 = new Intent(EditCollectionView.this, MainActivity.class);
                    DBHandler db = new DBHandler(EditCollectionView.this);
                    db.editCollection(nameField.getText().toString().trim(), intent.getIntExtra("collection_id", 0));
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    finish();
                }
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHandler db = new DBHandler(EditCollectionView.this);
                if (db.readAllDataFlashcards(id).getCount() == 0) {
                    Toast.makeText(EditCollectionView.this,"Please add a flashcard first",Toast.LENGTH_SHORT).show();
                } else {

                    File sharecsv = db.exportCollectionToFile(id, name);
                    Uri uri = FileProvider.getUriForFile(
                            EditCollectionView.this,
                            "com.example.cs4084_group_13.fileprovoider",
                            sharecsv);

                    Intent intent1 = new Intent(Intent.ACTION_SEND);
                    intent1.setType("text/csv");
                    intent1.putExtra(Intent.EXTRA_STREAM, uri);
                    intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent1 = Intent.createChooser(intent1, "Share CSV File");
                    startActivity(intent1);
                }
            }
        });




    }
}