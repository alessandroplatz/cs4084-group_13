package com.example.cs4084_group_13;
import java.util.ArrayList;

public class Collections {
    private String name;
    private ArrayList<Flashcard> flashcards;

    public Collections(String name) {
        this.name = name;
        this.flashcards = new ArrayList<>();
    }

    public ArrayList<Flashcard> getFlashcards() {
        return flashcards;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCard(Flashcard flashcard) {
        this.flashcards.add(flashcard);
    }

    public int getCardCount() {
        return this.flashcards.size();
    }
}
