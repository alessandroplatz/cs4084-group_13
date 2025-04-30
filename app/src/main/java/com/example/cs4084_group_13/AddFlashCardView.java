package com.example.cs4084_group_13;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddFlashCardView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_flash_card_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            TextView frontTitle =  findViewById(R.id.TitleFront);
            TextView backTitle = findViewById(R.id.TitleBack);
            EditText backET = findViewById(R.id.ETBack);
            EditText frontET = findViewById(R.id.ETFront);
            Button addBut = findViewById(R.id.subBut);

            Intent intent = getIntent();
            int colID = intent.getIntExtra("collection_id",0);//make sure to actually add it in prev scrren
            String colName = intent.getStringExtra("collection_name");

            addBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (backET.getText().toString().isEmpty()&&frontET.getText().toString().isEmpty()) {
                        Toast.makeText(AddFlashCardView.this,"Please don't Leave the Back and Front Empty!",Toast.LENGTH_SHORT).show();

                    } else if (backET.getText().toString().isEmpty()) {

                        Toast.makeText(AddFlashCardView.this,"Please don't Leave the Back Empty!",Toast.LENGTH_SHORT).show();
                    } else if (frontET.getText().toString().isEmpty()) {

                        Toast.makeText(AddFlashCardView.this,"Please don't Leave the Front Empty!",Toast.LENGTH_SHORT).show();
                    } else {
                        DBHandler myDB = new DBHandler(AddFlashCardView.this);
                        myDB.addFlashcard(frontET.getText().toString().trim(), backET.getText().toString().trim(), colID);
                        Intent intent = new Intent(AddFlashCardView.this, FlashCardView.class);
                        intent.putExtra("collection_id", colID);
                        intent.putExtra("collection_name", colName);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
            });



            return insets;
        });
    }
}