package com.example.inchat.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inchat.Activities.Groupgroup;
import com.example.inchat.Adapter.GroupAdapter;
import com.example.inchat.Adapter.NewUserAdapterClass;
import com.example.inchat.Models.Group;
import com.example.inchat.Models.Users;
import com.example.inchat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class homenewfragmenter extends Fragment {

    private RecyclerView recyclerView;
    private NewUserAdapterClass userAdapter;
    private List<Users> mUsers;
    private Toolbar t;
    private ImageView group;
    String getkey;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        t = view.findViewById(R.id.toolbar);
        group = view.findViewById(R.id.group);
        recyclerView = view.findViewById(R.id.displaynewview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = LayoutInflater.from(getContext());
                View promptsView = li.inflate(R.layout.alert_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());

                // set alert_dialog.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView.findViewById(R.id.etUserInput);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Group")
                                        .child(userInput.getText().toString()).push();
                                ref.setValue(userInput.getText().toString());
                                getkey = ref.getKey();
                                String name = userInput.getText().toString();

                                Group group = new Group(userInput.getText().toString(), getkey);

                                FirebaseDatabase.getInstance().getReference().child("Group")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getkey).child("name").setValue(name);
                                FirebaseDatabase.getInstance().getReference().child("Group")
                                        .child(name).removeValue();
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child("Groups")
                                        .child(getkey)
                                        .setValue(group);

                                Intent intent = new Intent(getActivity(), Groupgroup.class);
                                intent.putExtra("group", group.getName());
                                intent.putExtra("keymap", getkey);

                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        mUsers = new ArrayList<>();



        readuser();
        return view;
    }

    private void readuser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference nreference = FirebaseDatabase.getInstance().getReference("Users");

        reference.child(firebaseUser.getUid()).child("friends").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users user = dataSnapshot.getValue(Users.class);
                    mUsers.add(user);
                }
                userAdapter = new NewUserAdapterClass(getContext(), mUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       /* nreference.child(firebaseUser.getUid()).child("followers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Users user = dataSnapshot.getValue(Users.class);
                        mUsers.add(user);
                }
                userAdapter = new NewUserAdapterClass(getContext(), mUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });**/
    }
}