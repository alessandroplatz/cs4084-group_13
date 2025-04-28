package com.example.cs4084_group_13;

import androidx.annotation.NonNull;

public class Flashcard {
    private String front;
    private String back;
    private int id;
    private int colID;

    public Flashcard(String front, String back,int id,int colID) {
        this.front = front;
        this.back = back;
        this.id = id;
        this.colID = colID;
    }

    public String getBack() {
        return back;
    }

    public String getFront() {
        return front;
    }

    public int getid() {
        return this.id;
    }

    public int getColID() {
        return this.colID;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setColID(int colID) {
        this.colID = colID;
    }


    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
