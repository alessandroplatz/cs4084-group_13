package com.example.cs4084_group_13;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class FlashcardTestView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flashcard_test_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView countText = findViewById(R.id.TextTime);
        TextView titletime = findViewById(R.id.TitleTime);
        new CountDownTimer(4000,1000) {

            @Override
            public void onTick(long l) {
                int secs = (int) (l/1000);

                if (secs > 0) {
                    countText.setText(secs+"");
                } else {
                    countText.setText("Go");
                }
            }

            @Override
            public void onFinish() {
                countText.setVisibility(TextView.GONE);
                titletime.setVisibility(TextView.GONE);
            }
        }.start();
    }
}