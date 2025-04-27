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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FlashCardView extends AppCompatActivity implements RecyclerViewInterface{

    DBHandler db;
    ArrayList<String> fronts,backs;
    ArrayList<Integer> ids,colids;
    RecyclerView recyclerView;
    AdaptorForFlashcard adaptorForFlashcard;

    private Intent intent = null;
    private int colID = 0;//make sure to actually add it in prev scrren
    private String colName = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flash_card_view);

        recyclerView = findViewById(R.id.collGall);
        Button addButton = findViewById(R.id.button3);
        RecyclerViewInterface recyclerViewInterface;
        addButton.bringToFront();

        intent = getIntent();
        colID = intent.getIntExtra("collection_id",0);
        colName = intent.getStringExtra("collection_name");





        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FlashCardView.this, AddFlashCardView.class);
                intent.putExtra("collection_id",colID);
                intent.putExtra("collection_name",colName);
                startActivity(intent);
            }
        });



        db = new DBHandler(this);
        fronts = new ArrayList<>();
        ids = new ArrayList<>();
        backs = new ArrayList<>();
        colids = new ArrayList<>();
        storeDataInArrays(colID);
        adaptorForFlashcard = new AdaptorForFlashcard(this,ids,fronts,backs,colids,this);
        recyclerView.setAdapter(adaptorForFlashcard);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.flashcardV), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void storeDataInArrays(int colID) {
        Cursor cursor = db.readAllDataFlashcards(colID);//change int to adaptable id later
        if (cursor.getCount() ==0) {
            Toast.makeText(this,"No Collections",Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String s = cursor.getString(0);
                ids.add(cursor.getInt(0));
                fronts.add(cursor.getString(1));
                backs.add(cursor.getString(2));
                colids.add(cursor.getInt(3));
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(FlashCardView.this, FlashCardView.class);
        startActivity(intent);
    }
}