package com.example.inchat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inchat.Activities.MessageActivity;
import com.example.inchat.R;
import com.example.inchat.Models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mcontext;
    private List<Users>  mUser;
    FirebaseUser firebaseUser;
    DatabaseReference ref;

    public UserAdapter(Context mcontext, List<Users> mUser){
        this.mUser = mUser;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.useritem, parent, false);
        return new UserAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {

        firebaseUser  = FirebaseAuth.getInstance().getCurrentUser();
        Users user = mUser.get(position);
        holder.username.setText(user.getName());

        following(user.getId(), holder.followbuttonrequest);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, MessageActivity.class);
                intent.putExtra("name", user.getName());
                intent.putExtra("userid", user.getId());
                mcontext.startActivity(intent);
            }
        });

        holder.followbuttonrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.followbuttonrequest.getText().toString().equals("Follow")){
                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(firebaseUser.getUid())
                            .child("following")
                            .child(user.getId())
                            .setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(user.getId())
                            .child("followers")
                            .child(firebaseUser.getUid())
                            .setValue(true);
                }else{
                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(firebaseUser.getUid())
                            .child("following")
                            .child(user.getId())
                            .removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(user.getId())
                            .child("followers")
                            .child(firebaseUser.getUid())
                            .removeValue();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profilepic;
        public TextView followbuttonrequest;

        FirebaseDatabase ref;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.usernameholdernew);
            profilepic = itemView.findViewById(R.id.profileimageholdernew);
            followbuttonrequest = itemView.findViewById(R.id.follow);

            ref = FirebaseDatabase.getInstance();

        }}
        private void following(String userid, TextView followbuttonrequest){
            ref = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(firebaseUser.getUid())
                    .child("following");

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(userid).exists()){
                        followbuttonrequest.setText("Unfollow");
                    }else{
                        followbuttonrequest.setText("Follow");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

}
