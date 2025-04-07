package com.example.cs4084_group_13;

import androidx.annotation.NonNull;

public class Flashcard {
    private String front;
    private String back;
    private String name;

    public Flashcard(String front, String back) {
        this.front = front;
        this.back = back;
    }

    public String getBack() {
        return back;
    }

    public String getFront() {
        return front;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public void setFront(String front) {
        this.front = front;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
