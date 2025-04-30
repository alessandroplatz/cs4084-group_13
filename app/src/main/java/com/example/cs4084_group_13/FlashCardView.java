package com.example.cs4084_group_13;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
        TextView colltitle = findViewById(R.id.TitleV);
        Button testBut = findViewById(R.id.butBT);

        intent = getIntent();
        colID = intent.getIntExtra("collection_id",0);
        colName = intent.getStringExtra("collection_name");

        colltitle.setText(colName + "\n Flashcards");

        //Add toolbar
        BottomNavigationView toolBar = findViewById(R.id.bottomNavigationView);

        toolBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.Collections)
                {
                    Intent intent = new Intent(FlashCardView.this, MainActivity.class);
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

        testBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ids.isEmpty()) {
                    Toast.makeText(FlashCardView.this,"Please Make a Flashcard Before Trying a Test!",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(FlashCardView.this, FlashcardTestView.class);
                    intent.putExtra("collection_id", colID);
                    intent.putExtra("collection_name", colName);
                    startActivity(intent);
                }
            }
        });

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
        Intent intent = new Intent(FlashCardView.this, EditFlashcardView.class);
        intent.putExtra("front",fronts.get(position));
        intent.putExtra("back",backs.get(position));
        intent.putExtra("collection_id",colID);
        intent.putExtra("flashcard_id",ids.get(position));
        intent.putExtra("collection_name",colName);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {

    }
}