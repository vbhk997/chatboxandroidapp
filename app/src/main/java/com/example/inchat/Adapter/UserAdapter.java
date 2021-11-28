package com.example.inchat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inchat.Models.Request;
import com.example.inchat.R;
import com.example.inchat.Models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mcontext;
    private List<Users>  mUser;
    FirebaseUser firebaseUser;
    DatabaseReference ref;
    private String status = "new";
    DatabaseReference newref;

    public void setStatus(String status) {
        this.status = status;
    }

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

        following(user.getId(), holder.followbuttonrequest, holder.unfollow);

       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, MessageActivity.class);
                intent.putExtra("name", user.getName());
                intent.putExtra("userid", user.getId());
                mcontext.startActivity(intent);
            }
        });**/

        holder.followbuttonrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((holder.followbuttonrequest.getText().toString().equals("Follow"))&&(((user.getId()).equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))==false)){

                    Users request = new Users(user.getId(), user.getUsername(), "sent");
                    Users requestnew = new Users(firebaseUser.getUid(), firebaseUser.getDisplayName(), "recieved");
                    FirebaseDatabase.getInstance().getReference().child("Requests")
                            .child(firebaseUser.getUid())
                            .child("sent")
                            .child(user.getId())
                            .setValue(request);
                    FirebaseDatabase.getInstance().getReference().child("Requests")
                            .child(user.getId())
                            .child("recieved")
                            .child(firebaseUser.getUid())
                            .setValue(requestnew);
                }else{
                    FirebaseDatabase.getInstance().getReference().child("Requests")
                            .child(firebaseUser.getUid())
                            .child("sent")
                            .child(user.getId())
                            .removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Requests")
                            .child(user.getId())
                            .child("recieved")
                            .child(firebaseUser.getUid())
                            .removeValue();
                }
            }
        });

        holder.unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(firebaseUser.getUid())
                        .child("friends")
                        .child(user.getId())
                        .removeValue();
                FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(user.getId())
                        .child("friends")
                        .child(firebaseUser.getUid())
                        .removeValue();
                holder.unfollow.setVisibility(View.INVISIBLE);
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
        public ImageView unfollow;

        FirebaseDatabase ref;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.usernameholdernewfriendrequestnew);
            profilepic = itemView.findViewById(R.id.profileimageholdernewfriendrequestnew);
            followbuttonrequest = itemView.findViewById(R.id.follow);
            unfollow = itemView.findViewById(R.id.unfollow);

            ref = FirebaseDatabase.getInstance();

        }}
        private void following(String userid, TextView followbuttonrequest, ImageView unfollow){
            ref = FirebaseDatabase.getInstance().getReference().child("Requests")
                    .child(firebaseUser.getUid());

            DatabaseReference newref;

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    DatabaseReference newref;
                    newref = FirebaseDatabase.getInstance().getReference("Users")
                            .child(firebaseUser.getUid())
                            .child("friends");

                    if(snapshot.child("sent").child(userid).exists()){
                        followbuttonrequest.setText("Requested");
                    }if(!(snapshot.child("sent").child(userid).exists())){
                        followbuttonrequest.setText("Follow");
                        //Toast.makeText(mcontext, query.toString(), Toast.LENGTH_SHORT).show();
                    }newref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(userid)){
                                followbuttonrequest.setText("Unfriend");
                                unfollow.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

}
