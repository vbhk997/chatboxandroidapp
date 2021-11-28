package com.example.inchat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inchat.Activities.navactivity;
import com.example.inchat.Fragments.groupfragmenter;
import com.example.inchat.Fragments.homenewfragmenter;
import com.example.inchat.Models.Group;
import com.example.inchat.Models.Users;
import com.example.inchat.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class GroupFragmentAdapter extends RecyclerView.Adapter{

    private Context mcontext;
    private List<Group> group;
    FirebaseUser firebaseUser;
    DatabaseReference ref;
    String keymap, gname;

    public GroupFragmentAdapter(Context mcontext, List<Group> group) {
        this.mcontext = mcontext;
        this.group = group;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.groupitem, parent, false);
        return new returnviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Group groupnew = group.get(position);
        ((returnviewholder)holder).username.setText(groupnew.getName());
    }

    @Override
    public int getItemCount() {
        return group.size();
    }

    public class returnviewholder extends RecyclerView.ViewHolder{

        TextView username;
        ImageView add, usrepic;
        public returnviewholder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usrename);
            add = itemView.findViewById(R.id.addtogroup);
            usrepic = itemView.findViewById(R.id.usrepic);
        }
    }
}
