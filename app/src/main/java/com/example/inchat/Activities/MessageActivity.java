package com.example.inchat.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.inchat.Adapter.MessageAdapter;
import com.example.inchat.Models.Message;
import com.example.inchat.R;
import com.example.inchat.databinding.ActivityMessagenewBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

public class MessageActivity extends AppCompatActivity {

    ActivityMessagenewBinding binding;
    MessageAdapter adapter;
    ArrayList<Message> messages;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagenew);

        binding = ActivityMessagenewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        String name = getIntent().getStringExtra("name");
        String recieveruid = getIntent().getStringExtra("userid");
        String senderuid = FirebaseAuth.getInstance().getUid();
        String senderroom = senderuid+recieveruid;
        String recieverroom = recieveruid+senderuid;

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        messages = new ArrayList<>();

        adapter = new MessageAdapter(messages, this);

        binding.recyclerview.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerview.setLayoutManager(layoutManager);

        /*Context context = MessageActivity.this;

        /*File inchatdir = context.getDir("Inchatdir", Context.MODE_PRIVATE);
        //File with = new File(inchatdir, "chats");
        //FileOutputStream out = new FileOutputStream(with);
        //BufferedReader nw = new BufferedReader(new InputStreamReader(getAssets().open("senderroom.txt")));
        String filename = "myfile";
        String fileContents = "Hello world!";
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(fileContents.toByteArray());**/

        database.getReference().child("chats")
                .child(senderroom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot){
                        messages.clear();
                        for(DataSnapshot dataSnapshot:snapshot.getChildren())
                        {
                            Message model = dataSnapshot.getValue(Message.class);
                            messages.add(model);
                        }
                        adapter.notifyDataSetChanged();
                        binding.recyclerview.smoothScrollToPosition(binding.recyclerview.getAdapter().getItemCount());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  String message = binding.etmessage.getText().toString();
                  Date date = new Date();
                  final Message model = new Message(message, senderuid, date.getTime());
                  binding.etmessage.setText("");
                  database.getReference().child("chats")
                          .child(senderroom)
                          .push()
                          .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void unused) {
                          database.getReference().child("chats")
                                  .child(recieverroom)
                                  .push()
                                  .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void unused) {

                              }
                          });
                      }
                  });

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}



