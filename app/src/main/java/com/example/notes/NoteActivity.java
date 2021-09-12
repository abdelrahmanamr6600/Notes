package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {
    TextView title_textView,note_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        title_textView = findViewById(R.id.Title);
        note_textview = findViewById(R.id.note);
        Intent intent = getIntent();
         String title = intent.getExtras().getString("Title");
         String note = intent.getExtras().getString("Note");
title_textView.setText(title);
note_textview.setText(note);



    }
}