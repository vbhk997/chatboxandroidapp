package com.example.inchat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inchat.Models.Group;
import com.example.inchat.Models.Users;
import com.example.inchat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter{

    private Context mcontext;
    private List<Users> mUser;
    DatabaseReference ref;
    String keymap, gname;
    List<Users> newmUser;

    public GroupAdapter(Context mcontext, List<Users> mUser, String keymap, String gname) {
        this.mcontext = mcontext;
        this.mUser = mUser;
        this.keymap = keymap;
        this.gname = gname;
    }

    public GroupAdapter(List<Users> newmUser, String key) {
        for(int t = 0; t < newmUser.size(); t++){
            Users user = newmUser.get(t);
            FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(user.getId())
                    .child("Groups")
                    .child(key).removeValue();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.groupitem, parent, false);
        return new returnviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Users user = mUser.get(position);
        ((returnviewholder)holder).username.setText(user.getUsername());

        selected(keymap, ((returnviewholder)holder).add, user.getId(), ((returnviewholder)holder).grouprequest);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Group group = new Group(gname, keymap);
                if(/*((returnviewholder)holder).add.getVisibility() == View.INVISIBLE && ((returnviewholder)holder).grouprequest.getVisibility() == View.INVISIBLE**/
                        ((returnviewholder)holder).grouprequest.getText().toString().equals("Add")){
                    ((returnviewholder)holder).grouprequest.setText("Added");
                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(user.getId())
                            .child("Groups")
                            .child(keymap)
                            .setValue(group);
                    FirebaseDatabase.getInstance().getReference().child("Group")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(keymap).child("users").child(user.getId()).setValue(user);
                }else if(//((returnviewholder)holder).add.getVisibility() == View.VISIBLE
                        ((returnviewholder)holder).grouprequest.getText().toString().equals("Added"))// ){
                {((returnviewholder)holder).grouprequest.setText("Add");
                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(user.getId())
                            .child("Groups")
                            .child(keymap)
                            .removeValue();
                }else if(//((returnviewholder)holder).grouprequest.getVisibility() == View.VISIBLE)// {
                        ((returnviewholder)holder).grouprequest.getText().toString().equals("Request")){
                    if(((returnviewholder)holder).grouprequest.getText().toString().equals("Request")){
                        ((returnviewholder)holder).grouprequest.setText("Requested");
                    }else{
                        ((returnviewholder)holder).grouprequest.setText("Send Group Request");
                    }
                }
                else{
                    ((returnviewholder)holder).grouprequest.setText("Request");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class returnviewholder extends RecyclerView.ViewHolder{

        TextView username, grouprequest;
        ImageView add;
        public returnviewholder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usrename);
            add = itemView.findViewById(R.id.addtogroup);
            grouprequest = itemView.findViewById(R.id.groupfriendrequest);
        }
    }
    private void selected(String groupid, ImageView add, String userid, TextView grpreq){

        ref = FirebaseDatabase.getInstance().getReference().child("Users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("friends").child(userid).exists()){
                //add.setVisibility(View.INVISIBLE);
                    grpreq.setText("Add");
            }else{
                    //grpreq.setVisibility(View.VISIBLE);
                    grpreq.setText("Request");
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
