package com.example.inchat.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inchat.Adapter.UserAdapter;
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

public class serachboxnewfragment extends Fragment {

    EditText editText;
    Button newbtn;

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<Users> mUsers;

    TextView followbtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = view.findViewById(R.id.display);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        editText = view.findViewById(R.id.searchbar);
        followbtn = view.findViewById(R.id.follow);
        newbtn = view.findViewById(R.id.newsearch);

        mUsers = new ArrayList<>();

        readuser();
        return view;
    }

    private void readuser(){

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        newbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mUsers.clear();
                String t = editText.getText().toString();

                if (!(t.equals(""))){
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mUsers.clear();
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                            Users user = snapshot.getValue(Users.class);
                            if (user.getName().contains(editText.getText().toString()) || user.getUsername().contains(editText.getText().toString())) {
                                mUsers.add(user);
                            }
                        }
                        userAdapter = new UserAdapter(getContext(), mUsers);
                        recyclerView.setAdapter(userAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
                else{
                    mUsers.clear();
                    userAdapter = new UserAdapter(getContext(), mUsers);
                    recyclerView.setAdapter(userAdapter);
                }
}});}}
