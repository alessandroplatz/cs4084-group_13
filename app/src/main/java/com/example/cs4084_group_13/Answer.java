package com.example.cs4084_group_13;

import java.io.Serializable;

public class Answer implements Serializable {
    private Flashcard flashcard;
    private boolean correct;

    public Answer(Flashcard flashcard,boolean correct) {
        this.flashcard = flashcard;
        this.correct = correct;
    }

    public Flashcard getFlashcard() {
        return this.flashcard;
    }

    public void setFlashcard(Flashcard flashcard){
        this.flashcard = flashcard;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public boolean getCorrect() {
        return this.correct;
    }
}
