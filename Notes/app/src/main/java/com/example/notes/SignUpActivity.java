package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.notes.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth auth ;
    ActivitySignUpBinding activitySignUpBinding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(activitySignUpBinding.getRoot());
        auth = FirebaseAuth.getInstance();
   activitySignUpBinding.btnSignup.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           activitySignUpBinding.progressBarSignup.setVisibility(View.VISIBLE);
             auth.createUserWithEmailAndPassword(activitySignUpBinding.editEmailSignup.getText().toString(),activitySignUpBinding.editPasswordSignup.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {



                   activitySignUpBinding.progressBarSignup.setVisibility(View.GONE);
                   if (task.isSuccessful())
                   {
                       Toast.makeText(getApplicationContext(),"Thank you for your registration",Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(
                               getApplicationContext(),SigningActivity.class
                       ));
                   }


               }
           });
       }
   });
    }
}