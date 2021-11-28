package com.example.inchat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inchat.Models.Request;
import com.example.inchat.R;
import com.example.inchat.Models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private Context ncontext;
    private List<Users>  mUser;
    FirebaseUser firebaseUser;
    DatabaseReference ref;

    public NotificationsAdapter(Context ncontext, List<Users> mUser){
        this.mUser = mUser;
        this.ncontext = ncontext;
    }

    @NonNull
    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ncontext).inflate(R.layout.useritemfriendrequestnew, parent, false);
        return new NotificationsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.ViewHolder holder, int position) {
        firebaseUser  = FirebaseAuth.getInstance().getCurrentUser();
        Users user = mUser.get(position);
        holder.username.setText(user.getName());
        Users newuser = new Users(firebaseUser.getUid(), firebaseUser.getDisplayName(), "follow");

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Requests")
                        .child(firebaseUser.getUid())
                        .child("recieved")
                        .child(user.getId())
                        .removeValue();
                FirebaseDatabase.getInstance().getReference().child("Requests")
                        .child(user.getId())
                        .child("sent")
                        .child(firebaseUser.getUid())
                        .removeValue();
                FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(firebaseUser.getUid())
                        .child("friends")
                        .child(user.getId())
                        .setValue(user);
                FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(user.getId())
                        .child("friends")
                        .child(firebaseUser.getUid())
                        .setValue(newuser);
            }
        });

        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Requests")
                        .child(firebaseUser.getUid())
                        .child("recieved")
                        .child(user.getId())
                        .removeValue();
                FirebaseDatabase.getInstance().getReference().child("Requests")
                        .child(user.getId())
                        .child("sent")
                        .child(firebaseUser.getUid())
                        .removeValue();
            }
        });

      /*   following(user.getId(), holder.followbuttonrequest);

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
                            .setValue(user);
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
        });**/
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profilepic;
        public Button accept, decline;

        FirebaseDatabase ref;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.namholder);
            profilepic = itemView.findViewById(R.id.iholder);
            accept = itemView.findViewById(R.id.Accept);
            decline = itemView.findViewById(R.id.Decline);

            ref = FirebaseDatabase.getInstance();

        }}

}
