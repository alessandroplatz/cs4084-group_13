package com.example.cs4084_group_13;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

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
            ImageButton importButton = findViewById(R.id.importBut);

            subButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (name.getText().toString().isEmpty()) {
                        Toast.makeText(CreateCollectionActivity.this,"Please enter something!",Toast.LENGTH_SHORT).show();
                    } else {
                        DBHandler myDB = new DBHandler(CreateCollectionActivity.this);
                        myDB.addCollection(name.getText().toString().trim());
                        Intent intent = new Intent(CreateCollectionActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
            });


            importButton.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View view) {
                        importButton.setEnabled(false);
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        DBHandler db = new DBHandler(CreateCollectionActivity.this);
                        intent.setType("*/*");
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                     startActivityForResult(Intent.createChooser(intent, "Choose CSV File"), 1);
                        importButton.setEnabled(true);
                    }
                });


            return insets;
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            if (uri != null) {

                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    DBHandler db = new DBHandler(this);
                    File file = convertInputStreamToFile(inputStream,"import.csv",this);


                    boolean succ = db.importCSVToDB(file);

                    Intent intent = new Intent(CreateCollectionActivity.this, MainActivity.class);
                    if (succ) {
                        Toast.makeText(CreateCollectionActivity.this,"Collection Successfully Imported",Toast.LENGTH_SHORT).show();

                    }
                    finish();
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to read file", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public File convertInputStreamToFile(InputStream inputStream, String fileName, Context context) throws IOException {
        File tempFile = new File(context.getCacheDir(), fileName);
        try (OutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        }
        return tempFile;
    }



}
