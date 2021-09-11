package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes.Model.NotesModel;
import com.example.notes.MyAdapter.NotesAdapter;
import com.example.notes.databinding.ActivityMainBinding;
import com.example.notes.databinding.DeleteUpdateNoteBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
   ActivityMainBinding binding;
   Button save_btn;
   EditText title_Edit_Text,note_Edit_Text;
     public DatabaseReference myRef;

    String title_To_Firebase, note_To_Firebase;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    List <NotesModel> noteList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
         myRef = database.getReference("Notes");


       binding.btnAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showDialogNote();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        myRef.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noteList = new ArrayList<>();
                noteList.clear();
              for (DataSnapshot i :snapshot.getChildren()){
                     NotesModel note = i.getValue(NotesModel.class);
                     noteList.add(note);
              }
                NotesAdapter notesAdapter = new NotesAdapter(noteList, new OnRecyclerViewItemClickListener() {
                    @Override
                    public void OnItemClick(int noteId) {
                       Intent intent = new Intent(getApplicationContext(),NoteActivity.class);
                       NotesModel notesModel=  noteList.get(noteId);
                       intent.putExtra("Title",notesModel.getTitle() );
                        intent.putExtra("Note",notesModel.getNote());

                      startActivity(intent);

                    }

                    @Override
                    public void onLongclickIteam(int noteId) {
                        AlertDialog.Builder builder = new  AlertDialog.Builder(MainActivity.this);
                        View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.delete_update_note,null);
                        builder.setView(v);
                        AlertDialog alert = builder.create();
                        alert.show();
                        EditText title_edit_text =  v.findViewById(R.id.title_Edit_Text);
                        EditText note_Edit_text = v.findViewById(R.id.note_Edit_Text);
                        NotesModel notesModel =  noteList.get(noteId);
                        title_edit_text.setText(notesModel.getTitle());
                        note_Edit_text.setText(notesModel.getNote());
                        Button btn_update = v.findViewById(R.id.btn_update);
                        btn_update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                    String new_title = title_edit_text.getText().toString();
                                    String new_note = note_Edit_text.getText().toString();
                                   NotesModel note_after_update = new NotesModel(notesModel.getId(),new_title,new_note,getTime());
                                    myRef.child(notesModel.getId()).setValue(note_after_update);
                                alert.dismiss();


                            }


                        });
                        v.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                myRef.child(notesModel.getId()).removeValue();
                                alert.dismiss();
                            }
                        });


                    }
                });
              binding.noteListView.setAdapter(notesAdapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                binding.noteListView.setLayoutManager(layoutManager);
                binding.noteListView.setHasFixedSize(true);
                Collections.reverse(noteList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showDialogNote(){
        AlertDialog.Builder alert =   new AlertDialog.Builder(MainActivity.this);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_note_dialog,null);
      save_btn =  view.findViewById(R.id.btn_Save);
      title_Edit_Text = view.findViewById(R.id.title_Edit_Text);
        note_Edit_Text = view.findViewById(R.id.note_Edit_Text);
        alert.setView(view);
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
        title_Edit_Text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              if (charSequence.toString().trim().length()>0)
              {
                  save_btn.setEnabled(true);
              }
              else
              {
                  save_btn.setEnabled(false);
              }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        note_Edit_Text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length()>0)
                {
                    save_btn.setEnabled(true);
                }
                else
                {
                    save_btn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               title_To_Firebase=title_Edit_Text.getText().toString();
               note_To_Firebase = note_Edit_Text.getText().toString();
               String id = myRef.push().getKey();

                   NotesModel notesModel = new NotesModel(id,title_To_Firebase,note_To_Firebase,getTime());
                   myRef.child(auth.getUid()).child(id).setValue(notesModel);
                   alertDialog.dismiss();
           }
       });


    }
    public String getTime()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE hh:mm a");
         String strdate = dateFormat.format(calendar.getTime());
         return strdate;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.user_logout:
                auth.signOut();
                Intent logout_intent = new Intent(getApplicationContext(),SigningActivity.class);
                startActivity(logout_intent);

        }

        return super.onOptionsItemSelected(item);
    }
}