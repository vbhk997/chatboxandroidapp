package com.example.inchat.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inchat.Adapter.GroupMessageAdapter;
import com.example.inchat.Models.GroupModelForMessages;
import com.example.inchat.R;
import com.example.inchat.databinding.ActivityGroupMessageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class GroupMessage extends AppCompatActivity {

    ActivityGroupMessageBinding gbinding;
    GroupMessageAdapter gadapter, u;
    ArrayList<GroupModelForMessages> groupmessages;

    ImageView backbtn;
    TextView nt;

    FirebaseDatabase database;
    String grmp, grnm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_message);

        grmp = getIntent().getStringExtra("gtky");
        grnm = getIntent().getStringExtra("grnm");

        Toolbar toolbar = findViewById(R.id.grouptoolbar);
        setSupportActionBar(toolbar);

        //backbtn = findViewById(R.id.groupback);
        //backbtn.setClickable(true);
        //nt = (TextView) findViewById(R.id.groupvalues);

        //nt.setText("hello");

        getSupportActionBar().setTitle(grnm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gbinding = ActivityGroupMessageBinding.inflate(getLayoutInflater());
        setContentView(gbinding.getRoot());

        database = FirebaseDatabase.getInstance();
        String name = getIntent().getStringExtra("name");
        String recieveruid = getIntent().getStringExtra("userid");
        String senderuid = FirebaseAuth.getInstance().getUid();
        String senderroom = senderuid+recieveruid;
        String recieverroom = recieveruid+senderuid;

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        groupmessages = new ArrayList<>();

        u = new GroupMessageAdapter(senderuid);


        gadapter = new GroupMessageAdapter(groupmessages, this);

        gbinding.recyclerviewg.setAdapter(gadapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        gbinding.recyclerviewg.setLayoutManager(layoutManager);

        database.getReference().child("Groupch").child(grmp).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupmessages.clear();
                for(DataSnapshot t:snapshot.getChildren()){
                    GroupModelForMessages p = t.getValue(GroupModelForMessages.class);
                    groupmessages.add(p);
                }
                gadapter.notifyDataSetChanged();
                gbinding.recyclerviewg.smoothScrollToPosition(gbinding.recyclerviewg.getAdapter().getItemCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        gbinding.imageView9g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gmsg = gbinding.etmessageg.getText().toString();
                Date date = new Date();
                final GroupModelForMessages grpmodel = new GroupModelForMessages(gmsg, senderuid, date.getTime());
                gbinding.etmessageg.setText("");
                database.getReference().child("Groupch")
                        .child(grmp)
                        .push()
                        .setValue(grpmodel);
            }
        });

        /*gbinding.imageView9g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = gbinding.etmessageg.getText().toString();
                Date date = new Date();
                final Message model = new Message(message, senderuid, date.getTime());
                //binding.etmessage.setText("");
                database.getReference().child("Groupchats")
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
        });*/
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return super.onSupportNavigateUp();
    }
}