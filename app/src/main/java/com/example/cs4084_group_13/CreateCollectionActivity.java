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

public class CreateCollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_collection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            TextView nameTitle = findViewById(R.id.TitleET);
            EditText name = findViewById(R.id.enterNameInput);
            Button subButton = findViewById(R.id.add_button);

            subButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DBHandler myDB = new DBHandler(CreateCollectionActivity.this);
                    myDB.addCollection(name.getText().toString().trim());
                    Intent intent = new Intent(CreateCollectionActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            return insets;
        });
    }
}
