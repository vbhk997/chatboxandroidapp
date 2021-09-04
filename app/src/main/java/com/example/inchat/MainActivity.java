package com.example.inchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText username, email, phone, password;
    Button signup;
    TextView transitioner, login, newtext;

    FirebaseAuth auth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser User = auth.getCurrentUser();
        if(User!=null)
            startActivity(new Intent(MainActivity.this,navactivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.mname);
        email = findViewById(R.id.memail);
        phone = findViewById(R.id.mphone);
        password = findViewById(R.id.mpass);
        signup = findViewById(R.id.signup);
        transitioner = findViewById(R.id.transition);
        login = findViewById(R.id.login);
        newtext = findViewById(R.id.textView);

        auth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailid, passwords, usernames, phonenum;

                usernames = username.getText().toString();
                emailid = email.getText().toString();
                phonenum = phone.getText().toString();
                passwords = password.getText().toString();

                if(usernames.isEmpty()){
                    username.setError("Please Enter Username");
                    username.requestFocus();
                }if(emailid.isEmpty()){
                    email.setError("Please Enter Email");
                    email.requestFocus();

                }if(phonenum.isEmpty()){
                    phone.setError("Please Enter Phone number");
                    phone.requestFocus();

                }if(passwords.isEmpty()){
                    password.setError("Please Enter password");
                    password.requestFocus();

                }else{
                    auth.createUserWithEmailAndPassword(emailid,passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                startActivity(new Intent(MainActivity.this,navactivity.class));
                                finish();
                            }else{
                                Toast.makeText(MainActivity.this, "Signup invalid please try again", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
            }
        });

        transitioner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,Loginactivity.class));
            }
        });
    }

}