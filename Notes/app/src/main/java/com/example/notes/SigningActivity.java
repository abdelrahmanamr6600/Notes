package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.databinding.ActivityMainBinding;
import com.example.notes.databinding.ActivitySigninBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SigningActivity extends AppCompatActivity {
      public ActivitySigninBinding signingBinding;
     FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signingBinding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(signingBinding.getRoot());
        auth=FirebaseAuth.getInstance();
        signingBinding.btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                signingBinding.progressBarSignup.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(signingBinding.editEmail.getText().toString(), signingBinding.editPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                signingBinding.progressBarSignup.setVisibility(View.GONE);
                                Intent go_to_notes_activity = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(go_to_notes_activity);

                            }

                        }
                    });
            }
        });
        signingBinding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_to_signup;
                go_to_signup = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(go_to_signup);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser()!=null)
        {
            Intent go_to_notes_activity = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(go_to_notes_activity);
        }
    }
}