package com.example.inchat.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import com.example.inchat.Adapter.GroupAdapter;
import com.example.inchat.Adapter.UserAdapter;
import com.example.inchat.Fragments.groupfragmenter;
import com.example.inchat.Models.Users;
import com.example.inchat.R;
import com.example.inchat.databinding.ActivityGroupBinding;
import com.example.inchat.Fragments.homenewfragmenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Groupgroup extends AppCompatActivity {

    ActivityGroupBinding binding;

    Button button, search, make, notmake;
    EditText adduser, groupname;
    HorizontalScrollView horizontalScrollView;
    private List<Users> mUsers, list;
    private GroupAdapter userAdapter, cancel;
    String get, gname;
    String keymap, grp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        binding = ActivityGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        button = findViewById(R.id.button);
        search = findViewById(R.id.search);
        adduser = findViewById(R.id.adduser);
        groupname = findViewById(R.id.groupname);
        horizontalScrollView = findViewById(R.id.addeduser);
        mUsers = new ArrayList<>();
        list = new ArrayList<>();
        notmake = findViewById(R.id.dontcreategroup);
        make = findViewById(R.id.creategroup);

        get = getIntent().getStringExtra("keymap");
        gname = getIntent().getStringExtra("group");

        userAdapter = new GroupAdapter(this, mUsers, get, gname);

        binding.displayuser.setAdapter(userAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.displayuser.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        keymap = intent.getStringExtra("getkey");
        grp = intent.getStringExtra("group");

        readuser();
    }

    private void readuser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (adduser.getText().toString() != null) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            mUsers.clear();
                            for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                                Users user = datasnapshot.getValue(Users.class);
                                if (user.getName().contains(adduser.getText().toString()) || user.getUsername().contains(adduser.getText().toString())) {
                                    mUsers.add(user);
                                }
                            }
                            userAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUsers.clear();
                userAdapter.notifyDataSetChanged();
            }
        });

        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Groupgroup.this, navactivity.class));
            }
        });

        notmake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < mUsers.size(); i++){
                    Users muser = mUsers.get(i);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(muser.getId())
                            .child("Groups")
                            .child(keymap)
                            .child(grp).removeValue();
                }
                FirebaseDatabase.getInstance().getReference("Group")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(keymap)
                        .child(grp).removeValue();
                startActivity(new Intent(Groupgroup.this, navactivity.class));
            }
        });
    }
}
