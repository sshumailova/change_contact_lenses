package com.android.timetochangecontactlenses.db;

import java.util.ArrayList;

public class Note {
    public static ArrayList<Note> noteArrayList=new ArrayList<>();
    private int id;
    private String first;
    private String last;

    public Note(int id, String first, String last) {
        this.id = id;
        this.first = first;
        this.last = last;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
