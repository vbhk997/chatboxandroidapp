package com.example.inchat.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inchat.R;
import com.example.inchat.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText username, email, phone, password;
    Button signup;
    TextView transitioner, login, newtext;

    FirebaseAuth auth;

    FirebaseDatabase database;
    DatabaseReference ref;
    Users users;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser User = auth.getCurrentUser();
        if (User != null)
            startActivity(new Intent(MainActivity.this, navactivity.class));
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
        database = FirebaseDatabase.getInstance();

        ref = FirebaseDatabase.getInstance().getReference().child("Users");
        users = new Users();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailid, passwords, usernames, phonenum;

                usernames = username.getText().toString().trim();
                emailid = email.getText().toString().trim();
                phonenum = phone.getText().toString().trim();
                passwords = password.getText().toString().trim();
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
                                Users user = new Users(usernames, emailid, phonenum);
                                String id = task.getResult().getUser().getUid();
                                Users datanewvaluser = new Users(id);
                                Users finaluservalue = new Users(usernames, emailid, phonenum,id);
                                database.getReference().child("Users")
                                        .child(id)
                                        .setValue(finaluservalue);
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