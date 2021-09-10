package com.example.notes.Model;

import java.util.Date;
import java.util.Map;

public class NotesModel {
    String title,note;
    String id;
    String timestamp;

    public NotesModel() {
    }

    public NotesModel(String id, String title, String note ,String timestamp) {
        this.title = title;
        this.note = note;
        this.id = id;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
