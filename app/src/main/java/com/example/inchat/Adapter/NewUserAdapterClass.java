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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class NewUserAdapterClass extends RecyclerView.Adapter<NewUserAdapterClass.ViewHolder> {

    private Context mcontext;
    private List<Users>  mUser;
    FirebaseUser firebaseUser;
    DatabaseReference ref;

    public NewUserAdapterClass(Context mcontext, List<Users> mUser){
        this.mUser = mUser;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public NewUserAdapterClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.homeuseritem, parent, false);
        return new NewUserAdapterClass.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NewUserAdapterClass.ViewHolder holder, int position) {
       firebaseUser  = FirebaseAuth.getInstance().getCurrentUser();
        Users user = mUser.get(position);
        holder.username.setText(user.getName());

       // following(user.getId(), holder.followbuttonrequest);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, MessageActivity.class);
                intent.putExtra("name", user.getName());
                intent.putExtra("userid", user.getId());
                mcontext.startActivity(intent);
            }
        });

        /*holder.followbuttonrequest.setOnClickListener(new View.OnClickListener() {
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
        public ImageView groupchatmaker;

        FirebaseDatabase ref;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.usernameholdernewfriendrequest);
            profilepic = itemView.findViewById(R.id.profileimageholdernewfriendrequest);
            groupchatmaker = itemView.findViewById(R.id.group);

            ref = FirebaseDatabase.getInstance();

        }}

}
