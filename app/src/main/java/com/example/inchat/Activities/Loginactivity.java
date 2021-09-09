package com.example.inchat.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.inchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Loginactivity extends AppCompatActivity {

    EditText emailids, passworderinput;
    Button signin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        emailids = findViewById(R.id.emails);
        passworderinput = findViewById(R.id.passworder);
        signin = findViewById(R.id.btnsignin);

        mAuth = FirebaseAuth.getInstance();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(emailids.getText().toString(),passworderinput.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            startActivity(new Intent(Loginactivity.this,navactivity.class));
                            finish();
                        }else{
                            Toast.makeText(Loginactivity.this, "Please try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

}