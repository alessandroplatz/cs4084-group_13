package com.example.cs4084_group_13;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class FlashcardTestView extends AppCompatActivity {

    private int score = 0;
    private int colid;
    private int streak = 0;
    private int question = 1;
    private int bestStreak = 0;
    private int totalques = 0;
    private Flashcard currentFC;
    private boolean finishTest = false;
    private String curDate;
    private String FCBack;
    private String FCFront;
    private String colname;
    private TextView back;
    private TextView front;
    private TextView scoreEnd;
    private TextView highStreakEnd;
    private TextView userans;
    private EditText answer;
    private ArrayList<Flashcard> flashcards;
    private ArrayList<Answer> answers;
    private LinearLayout answerbox;
    private LinearLayout confirmBox;
    private LinearLayout resultsBox;
    private LinearLayout failureBox;
    private LinearLayout successBox;
    private LinearLayout endTestbox;
    private TextView longestStreak;
    private TextView questionCount;
    private TextView currentStreak;
    private Button NQFBut;
    private Button reviewansbut;
    private Button tryAgainBut;
    private Button backButton;
    private Button subBut;
    private Button NTTbut;
    private Button SOBut;
    private Button NQBut;
    private Button endTestYesBut;
    private Button endTestNoBut;


    

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


        Intent intent = getIntent();
        colid = intent.getIntExtra("collection_id",0);
        colname = intent.getStringExtra("collection_name");
        DBHandler db = new DBHandler(FlashcardTestView.this);
        flashcards = db.getFlashcardsForTest(colid);
        db.close();



        curDate = getCurrentDateTime();
        TextView countText = findViewById(R.id.TextTime);
        TextView titletime = findViewById(R.id.TitleTime);
        userans = findViewById(R.id.userAnsTV);
        answer = findViewById(R.id.answerET);
        answerbox = findViewById(R.id.flashcardLL);
        confirmBox = findViewById(R.id.resultBoxLL);
        failureBox = findViewById(R.id.FailureLL);
        successBox = findViewById(R.id.SuccessLL);
        resultsBox = findViewById(R.id.ResultScreen);
        subBut = findViewById(R.id.subBut);
        NTTbut = findViewById(R.id.NTTbut);
        SOBut = findViewById(R.id.SObut);
        NQBut = findViewById(R.id.NQbut);
        endTestYesBut = findViewById(R.id.endTestYesBut);
        endTestNoBut = findViewById(R.id.endTestNoBut);

        userans.setMovementMethod(new android.text.method.ScrollingMovementMethod());


        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (resultsBox.getVisibility() == View.VISIBLE) {
                    finish();
                } else {
                    endTestbox = findViewById(R.id.endTestLL);
                    endTestbox.setVisibility(View.VISIBLE);
                    answer.setEnabled(false);
                    subBut.setEnabled(false);
                    NTTbut.setEnabled(false);
                    SOBut.setEnabled(false);
                    NQBut.setEnabled(false);
                }
            }
        });

        endTestNoBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTestbox.setVisibility(View.INVISIBLE);
                answer.setEnabled(true);
                subBut.setEnabled(true);
                NTTbut.setEnabled(true);
                SOBut.setEnabled(true);
                NQBut.setEnabled(true);
            }
        });

        endTestYesBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



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

        answers = new ArrayList<>();



        currentFC = flashcards.remove(0);
         FCFront = currentFC.getFront();
         FCBack = currentFC.getBack();
        int FCid = currentFC.getid();
        front = findViewById(R.id.frontTV);
        back = findViewById(R.id.backTV);
        front.setMovementMethod(new android.text.method.ScrollingMovementMethod());
        back.setMovementMethod(new android.text.method.ScrollingMovementMethod());
        currentStreak = findViewById(R.id.YCSTV);
        scoreEnd = findViewById(R.id.scoreEnd);
        highStreakEnd = findViewById(R.id.highestStreakEnd);
        longestStreak = findViewById(R.id.LSTV);
        questionCount = findViewById(R.id.QuestionCountTV);
        reviewansbut = findViewById(R.id.reviewanswerbut);
        tryAgainBut = findViewById(R.id.tryAgainButton);
        backButton = findViewById(R.id.backButtonEnd);
        NQFBut = findViewById(R.id.NQFbut);

        front.setText(FCFront);


        subBut.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          onSubmit();//brings you to confirm screen
                                      }
                                  });



                NTTbut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        wrongAnswer();
                    }

                });

                SOBut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rightAnswer();
                    }
                });

                NQBut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nextQuestion(successBox);
                    }
                });

                NQFBut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nextQuestion(failureBox);
                    }
                });

                tryAgainBut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(FlashcardTestView.this, FlashcardTestView.class);
                        intent1.putExtra("collection_id",colid);
                        finish();
                        startActivity(intent1);
                    }
                });

                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(FlashcardTestView.this,FlashCardView.class);
                        intent1.putExtra("collection_id",colid);
                        intent1.putExtra("collection_name",colname);
                        startActivity(intent1);
                    }
                });




                answerbox.setVisibility(TextView.VISIBLE);


            }



            public void transition(View from, View to,Runnable onEnd) {
                from.animate()
                        .alpha(0f)
                        .setDuration(500)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                from.setVisibility(View.GONE);


                                to.setAlpha(0f);
                                to.setVisibility(View.VISIBLE);
                                to.animate()
                                        .alpha(1f)
                                        .setDuration(500)
                                        .withEndAction(onEnd)
                                        .start();
                            }
                        }).start();
            }

            public void onSubmit() {
                if (answer.getText().toString().isEmpty()) {
                    Toast.makeText(FlashcardTestView.this,"Please Attempt the Question!",Toast.LENGTH_SHORT).show();
                } else {
                    subBut.setEnabled(false);
                    userans.setText(answer.getText());
                    back.setText(FCBack);
                    //before check for finish

                    transition(answerbox, confirmBox, new Runnable() {
                        @Override
                        public void run() {
                            subBut.setEnabled(true);
                        }
                    });

                }

            }

            public void wrongAnswer() {
                SOBut.setEnabled(false);
                NTTbut.setEnabled(false);
                storeAnswer(false);
                if (!flashcards.isEmpty()) {
                    currentFC = flashcards.remove(0);
                } else {
                    finishTest = true;
                }

                if (bestStreak < streak) {
                    bestStreak = streak;
                }
                streak = 0;
                totalques++;
                if (finishTest) {
                    finishQuiz();
                } else {
                    longestStreak.setText("Longest Streak\n" + bestStreak);
                    transition(confirmBox, failureBox, new Runnable() {
                        @Override
                        public void run() {
                            SOBut.setEnabled(true);
                            NTTbut.setEnabled(true);
                        }
                    });
                }

            }

            public void rightAnswer() {
                storeAnswer(true);
                SOBut.setEnabled(false);
                NTTbut.setEnabled(false);
                if (!flashcards.isEmpty()) {
                    currentFC = flashcards.remove(0);
                } else {
                    finishTest = true;
                }
                streak++;
                score++;
                totalques++;
                if (finishTest) {
                    finishQuiz();
                } else {
                    currentStreak.setText("Your Current Streak\n " + streak);

                    transition(confirmBox, successBox, new Runnable() {
                        @Override
                        public void run() {
                            SOBut.setEnabled(true);
                            NTTbut.setEnabled(true);
                        }
                    });
                }
            }


            public void finishQuiz() {
                if (streak > bestStreak) {
                    bestStreak = streak;
                }
                writeToTestHistory();
                highStreakEnd.setText("Highest Streak: " + bestStreak);
                scoreEnd.setText("Score: " + score+"/"+totalques);
                transition(confirmBox, resultsBox, new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }

            public void nextQuestion(View from) {
                FCBack = currentFC.getBack();
                answer.setText("");
                FCFront = currentFC.getFront();
                back.setText(FCBack);
                front.setText(FCFront);
                question++;
                questionCount.setText("Question. " + question);
                NQBut.setEnabled(false);
                NQFBut.setEnabled(false);


                transition(from, answerbox, new Runnable() {
                    @Override
                    public void run() {
                        NQBut.setEnabled(true);
                        NQFBut.setEnabled(true);
                    }
                });
            }

            public void storeAnswer(boolean correct) {
                answers.add(new Answer(currentFC,correct));
            }

            public String getCurrentDateTime() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
                return simpleDateFormat.format(new Date());
            }

            public void writeToTestHistory() {
                DBHandler db = new DBHandler(this);
                db.addToTestHistory(score,curDate,totalques,colid);
                db.close();
            }
}



