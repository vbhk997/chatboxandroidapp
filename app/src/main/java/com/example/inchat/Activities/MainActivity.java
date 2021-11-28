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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    EditText username, email, phone, password, usernamenewusername;
    Button signup;
    TextView transitioner, message, newtext;

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
        usernamenewusername = findViewById(R.id.musername);
        email = findViewById(R.id.memail);
        phone = findViewById(R.id.mphone);
        password = findViewById(R.id.mpass);
        transitioner = findViewById(R.id.transition);
        signup = findViewById(R.id.signup);
        newtext = findViewById(R.id.textView);
        message = findViewById(R.id.passworderr);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        ref = FirebaseDatabase.getInstance().getReference().child("Users");
        users = new Users();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailid, passwords, usernames, phonenum, usernamesnewusernames;

                usernames = username.getText().toString().trim();
                emailid = email.getText().toString().trim();
                phonenum = phone.getText().toString().trim();
                passwords = password.getText().toString().trim();
                usernamesnewusernames = usernamenewusername.getText().toString().trim();
                StringTokenizer tokens = new StringTokenizer(passwords);

                if(usernames.isEmpty()){
                    username.setError("Please Enter Name");
                    username.requestFocus();
                }if(emailid.isEmpty()){
                    email.setError("Please Enter Email");
                    email.requestFocus();

                }if(phonenum.isEmpty()){
                    phone.setError("Please Enter Phone Number");
                    phone.requestFocus();

                }if(passwords.isEmpty()){
                    password.setError("Please Enter Password");
                    password.requestFocus();

                }if(usernamesnewusernames.isEmpty()) {
                    usernamenewusername.setError("Please Enter Username");
                    usernamenewusername.requestFocus();
                }else{
                    auth.createUserWithEmailAndPassword(emailid,passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Users user = new Users(usernames, emailid, phonenum);
                                String id = task.getResult().getUser().getUid();
                                Users datanewvaluser = new Users(id);
                                Users finaluservalue = new Users(usernames, emailid, phonenum, id, usernamesnewusernames);
                                database.getReference().child("Users")
                                        .child(id)
                                        .setValue(finaluservalue);
                                startActivity(new Intent(MainActivity.this,navactivity.class));

                                FirebaseUser user1 = auth.getCurrentUser();

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(usernamesnewusernames).build();

                                user1.updateProfile(profileUpdates);
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