package com.example.cs4084_group_13;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FlashcardTestView extends AppCompatActivity {

    private int score = 0;
    private int streak = 0;
    private int question = 1;
    private int bestStreak = 0;
    private Flashcard currentFC;
    private boolean finishTest = false;
    private String FCBack;
    private String FCFront;
    

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
                testStart();
            }
        }.start();
    }

    public void testStart() {
        Intent intent = getIntent();
        int colid = intent.getIntExtra("collection_id",0);
        DBHandler db = new DBHandler(FlashcardTestView.this);
        ArrayList<Flashcard> flashcards = db.getFlashcardsForTest(colid);



        currentFC = flashcards.remove(0);
         FCFront = currentFC.getFront();
         FCBack = currentFC.getBack();
        int FCid = currentFC.getid();

        LinearLayout answerbox = findViewById(R.id.flashcardLL);
        LinearLayout confirmBox = findViewById(R.id.resultBoxLL);
        LinearLayout failureBox = findViewById(R.id.FailureLL);
        LinearLayout successBox = findViewById(R.id.SuccessLL);
        TextView front = findViewById(R.id.frontTV);
        TextView back = findViewById(R.id.backTV);
        TextView currentStreak = findViewById(R.id.YCSTV);
        TextView longestStreak = findViewById(R.id.LSTV);
        TextView questionCount = findViewById(R.id.QuestionCountTV);
        Button subBut = findViewById(R.id.subBut);
        Button NTTbut = findViewById(R.id.NTTbut);
        Button SOBut = findViewById(R.id.SObut);
        Button NQBut = findViewById(R.id.NQbut);

        front.setText(FCFront);


        subBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back.setText(FCBack);
                if (!flashcards.isEmpty()) {
                    currentFC = flashcards.remove(0);
                } else {
                    finishTest = true;
                }

                answerbox.animate()
                                .alpha(0f)
                                        .setDuration(500)
                                                .withEndAction(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        answerbox.setVisibility(View.GONE);


                                                        confirmBox.setAlpha(0f);
                                                        confirmBox.setVisibility(View.VISIBLE);
                                                        confirmBox.animate()
                                                                .alpha(1f)
                                                                .setDuration(500)
                                                                .start();
                                                    }
                                                }).start();





            }
        });

        NTTbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bestStreak < streak) {
                    bestStreak = streak;
                }
                streak = 0;
                if (finishTest) {
                    confirmBox.animate()
                            .alpha(0f)
                            .setDuration(500)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    confirmBox.setVisibility(View.GONE);

                                    confirmBox.setAlpha(0f);
                                }
                            }).start();
                } else {
                    longestStreak.setText("Longest Streak\n" + bestStreak);
                    confirmBox.animate()
                            .alpha(0f)
                            .setDuration(500)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    confirmBox.setVisibility(View.GONE);

                                    confirmBox.setAlpha(0f);
                                    failureBox.setVisibility(View.VISIBLE);
                                    failureBox.animate()
                                            .alpha(1f)
                                            .setDuration(500)
                                            .start();
                                }
                            }).start();
                    //set streak text
                }
            }
        });

        SOBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                streak++;
                score++;
                if (finishTest) {
                    confirmBox.animate()
                            .alpha(0f)
                            .setDuration(500)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    confirmBox.setVisibility(View.GONE);

                                    confirmBox.setAlpha(0f);
                                }
                            }).start();
                } else {
                    currentStreak.setText("Your Current Streak\n " + streak);

                    confirmBox.animate()
                            .alpha(0f)
                            .setDuration(500)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    confirmBox.setVisibility(View.GONE);

                                    confirmBox.setAlpha(0f);
                                    successBox.setVisibility(View.VISIBLE);
                                    successBox.animate()
                                            .alpha(1f)
                                            .setDuration(500)
                                            .start();
                                }
                            }).start();
                }
            }
        });

        NQBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FCBack =currentFC.getBack();
                FCFront = currentFC.getFront();
                back.setText(FCBack);
                front.setText(FCFront);
                question++;
                questionCount.setText("Question. "+question);

                successBox.animate()
                        .alpha(0f)
                        .setDuration(500)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                successBox.setVisibility(View.GONE);

                                successBox.setAlpha(0f);
                                answerbox.setVisibility(View.VISIBLE);
                                answerbox.animate()
                                        .alpha(1f)
                                        .setDuration(500)
                                        .start();
                            }
                        }).start();
            }
        });




        answerbox.setVisibility(TextView.VISIBLE);


    }
}
